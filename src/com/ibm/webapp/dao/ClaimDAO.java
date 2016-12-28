package com.ibm.webapp.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.webapp.bean.ClaimDetails;
import com.ibm.webapp.mockdata.MockDataHelper;

public class ClaimDAO {

	private static final Map<String, ClaimDetails> _claimStore = new LinkedHashMap<>();

	private static void init() {
		if (_claimStore.size() == 0) {
			loadClaimsFromMockStore();
		}
	}

	/**
	 * Returns a claim details
	 * @param claimId
	 * @return
	 */
	public static ClaimDetails getClaimDetailsForProvider(String claimId) {
		init();
		return _claimStore.get(claimId);

	}

	/**
	 * Returns the list of claims for the provider
	 * @return
	 */
	public static List<ClaimDetails> getClaimListForProvider() {
		init();
		List<ClaimDetails> list = new ArrayList<>();
		for(ClaimDetails claim: _claimStore.values())
		{
			if(claim.getCurrentOwner()==null)
			{
				list.add(claim);
			}
		}
		return list;

	}

	private static void loadClaimsFromMockStore() {
		Gson gson = new GsonBuilder().create();
		ClaimDetails[] claimList = gson.fromJson(MockDataHelper.getClaimData(),
				ClaimDetails[].class);
		// No checking
		for (ClaimDetails claim : claimList) {
			_claimStore.put(claim.getClaimId(), claim);
		}
	}

}
