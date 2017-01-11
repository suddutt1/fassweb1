package com.ibm.webapp.dao;

import static com.ibm.webapp.action.ApplicationConstants.CLAIM_OWNER_HOME;
import static com.ibm.webapp.action.ApplicationConstants.CLAIM_OWNER_HOST;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.utils.MongoHelper;
import com.ibm.utils.PropertyManager;
import com.ibm.webapp.bean.ClaimDetails;
import com.ibm.webapp.bean.ClaimStatus;
import com.ibm.webapp.mockdata.MockDataHelper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

public class ClaimDAO {

	private static final String _RECORD_COLLECTION_NAME = "temp3";
	private static final Logger _LOGGER = Logger.getLogger(ClaimDAO.class
			.getName());

	// private static final Map<String, ClaimDetails> _claimStore = new
	// LinkedHashMap<>();

	/*
	 * private static void init() { if (_claimStore.size() == 0) {
	 * loadClaimsFromMockStore(); } }
	 */

	/**
	 * Returns a claim details
	 * 
	 * @param claimId
	 * @return
	 */
	public static ClaimDetails getClaimDetailsForProvider(String claimId) {
		return getClaimDetails(claimId);
	}

	/**
	 * Returns the list of claims for the provider
	 * getClaimDetails
	 * @return
	 */
	public static List<ClaimDetails> getClaimListForProvider() {
		
		List<ClaimDetails> list = new ArrayList<>();
		for (ClaimDetails claim : getAllClaims()) {
			if (claim.getCurrentOwner() == null) {
				list.add(claim);
			}
		}
		return list;

	}

	/**
	 * Returns the list of claims for the home entity
	 * 
	 * @return List of claims for the home entity
	 */
	public static List<ClaimDetails> getClaimListForHome() {
		
		List<ClaimDetails> list = new ArrayList<>();
		for (ClaimDetails claim : getAllClaims()) {
			if (CLAIM_OWNER_HOME.equals(claim.getCurrentOwner())) {
				list.add(claim);
			}
		}
		return list;

	}

	/**
	 * Returns the list of claims for the host
	 * 
	 * @return
	 */
	public static List<ClaimDetails> getClaimListForHost(ClaimStatus status) {
		
		List<ClaimDetails> list = new ArrayList<>();
		for (ClaimDetails claim :getAllClaims()) {
			if (CLAIM_OWNER_HOST.equals(claim.getCurrentOwner())
					&& status.equals(claim.getStatus())) {
				list.add(claim);
			}
		}
		return list;

	}

	/**
	 * Update the claim details in DB
	 * 
	 * @param claimDetails
	 * @return
	 */
	public static boolean updateClaim(ClaimDetails claimDetails) {
		
		String claimId  = claimDetails.getClaimId();
		ClaimDetails searchItem  = new ClaimDetails();
		searchItem.setClaimId(claimId);
		MongoCollection<Document> collection = getCollection();
		if(collection!=null)
		{
			UpdateResult result  = collection.updateOne(searchItem.buildFilter(),new Document("$set",claimDetails));
			return (result.getModifiedCount()>0?true:false);
		
		}
		return false;
	}

	/*
	 * private static void loadClaimsFromMockStore() { Gson gson = new
	 * GsonBuilder().create(); ClaimDetails[] claimList =
	 * gson.fromJson(MockDataHelper.getClaimData(), ClaimDetails[].class); // No
	 * checking for (ClaimDetails claim : claimList) {
	 * _claimStore.put(claim.getClaimId(), claim); } }
	 */

	/**
	 * Write data into data store from a json file. This method is used as
	 * one-of process during db population.
	 * 
	 * @param claims
	 * @return
	 */
	public static boolean loadDataStore(List<ClaimDetails> claims) {
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			// collection.drop();
			for (ClaimDetails claim : claims) {
				collection.insertOne(Document.parse(claim.toJson()));
			}
			return true;
		}

		return false;
	}

	private static List<ClaimDetails> getAllClaims() {
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			List<ClaimDetails> outputList = new ArrayList<>();
			for(Document doc: collection.find())
			{
				ClaimDetails existingClaim = new ClaimDetails();	
				existingClaim.buildInstance(doc);
				outputList.add(existingClaim);
			}
			return outputList;
		}
		return null;
	}
	private static ClaimDetails getClaimDetails(String claimId) {
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			ClaimDetails searchObj = new ClaimDetails();
			searchObj.setClaimId(claimId);
			Document existingDoc = collection.find(searchObj.buildFilter()).first();
			if(existingDoc!=null)
			{
				ClaimDetails existingClaim = new ClaimDetails();	
				existingClaim.buildInstance(existingDoc);
				return existingClaim;
			}
		}
		return null;
	}

	private static MongoCollection<Document> getCollection() {
		boolean isInitialized = MongoHelper.isInitialized();
		if (!isInitialized) {
			isInitialized = MongoHelper.init(PropertyManager
					.getProperties(MongoHelper.MONGO_PROPERTY_BUNCH));
		}
		_LOGGER.log(Level.WARNING, "Mongo helper initialiation status "
				+ isInitialized);
		if (isInitialized) {
			MongoCollection<Document> collection = MongoHelper
					.getCollection(_RECORD_COLLECTION_NAME);
			return collection;

		}
		return null;
	}
}
