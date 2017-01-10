package com.ibm.webapp.hldao;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.hyperledger.client.HyperLedgerRequest;
import com.ibm.hyperledger.client.HyperLedgerResponse;
import com.ibm.hyperledger.client.HyperledgerClient;
import com.ibm.utils.PropertyManager;
import com.ibm.webapp.bean.ClaimDetails;

import static com.ibm.webapp.action.ApplicationConstants.*;

public class ClaimHLDAO {

	private static final Logger _LOGGER = Logger.getLogger(ClaimHLDAO.class
			.getName());
	private static Map<String, Map<String, String>> _credentialMap = null;
	private static String _hyperLedgerUrl = null;
	private static String _chainCode = null;

	public static HyperLedgerResponse initiaClaimProcess(
			ClaimDetails claimDetails) {
		HyperLedgerResponse response = null;
		try {
			HyperledgerClient client = getClient(CLAIM_OWNER_PROVIER);
			if (client.register()) {
				HyperLedgerRequest claimRequest = createClaimRequest(
						claimDetails, getUserId(CLAIM_OWNER_PROVIER));
				response = client.invokeMethod(claimRequest);
			} else {
				response = new HyperLedgerResponse(false);
				response.setMessage("Registration failed");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "HL initiaClaimProcess failed with ", ex);
			response = new HyperLedgerResponse(false);
			response.setMessage("Exception in initializing the claim process:"
					+ ex.getMessage());
		}
		return response;
	}

	public static HyperLedgerResponse getClaimDetailsForHost(String claimId) {
		HyperLedgerResponse response = null;
		try {
			HyperledgerClient client = getClient(CLAIM_OWNER_HOST);
			if (client.register()) {
				HyperLedgerRequest claimRequest = createQueryRequest(claimId,
						getUserId(CLAIM_OWNER_PROVIER));
				response = client.invokeMethod(claimRequest);
			} else {
				response = new HyperLedgerResponse(false);
				response.setMessage("Registration failed");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "HL initiaClaimProcess failed with ", ex);
			response = new HyperLedgerResponse(false);
			response.setMessage("Exception in initializing the claim process:"
					+ ex.getMessage());
		}
		return response;
	}

	public static HyperLedgerResponse sendClaimToHome(String claimId) {
		HyperLedgerResponse response = null;
		try {
			HyperledgerClient client = getClient(CLAIM_OWNER_HOST);
			if (client.register()) {
				HyperLedgerRequest claimRequest = createTransferRequest(
						claimId, getUserId(CLAIM_OWNER_HOST),
						"transfer_to_host");
				response = client.invokeMethod(claimRequest);
			} else {
				response = new HyperLedgerResponse(false);
				response.setMessage("Registration failed");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "HL initiaClaimProcess failed with ", ex);
			response = new HyperLedgerResponse(false);
			response.setMessage("Exception in initializing the claim process:"
					+ ex.getMessage());
		}
		return response;
	}

	private static HyperLedgerRequest createTransferRequest(String claimId,
			String userId, String functionName) {
		HyperLedgerRequest request = new HyperLedgerRequest();
		request.setMethod("invoke");
		request.setChainCodeName(_chainCode);
		request.setCallFunction(functionName);
		request.setFunctionArgs(new String[] { userId, claimId });
		request.setSecureContext(userId);
		return request;
	}

	private static HyperLedgerRequest createQueryRequest(String claimId,
			String userId) {
		HyperLedgerRequest request = new HyperLedgerRequest();
		request.setMethod("query");
		request.setChainCodeName(_chainCode);
		request.setCallFunction("get_claim_details");
		request.setFunctionArgs(new String[] { userId, claimId });
		request.setSecureContext(userId);
		return request;
	}

	private static HyperLedgerRequest createClaimRequest(
			ClaimDetails claimDetails, String userId) {
		HyperLedgerRequest request = new HyperLedgerRequest();
		request.setMethod("invoke");
		request.setChainCodeName(_chainCode);
		request.setFunctionArgs(new String[] { claimDetails.getClaimId(),
				claimDetails.getServiceDate(), claimDetails.getAdmissionDate(),
				claimDetails.getProviderId(), claimDetails.getMemberId(),
				claimDetails.getSubscriberId(), claimDetails.getDiagCode(),
				claimDetails.getProcedureCode(),
				claimDetails.getProcedureDate(), claimDetails.getBillCode(),
				claimDetails.getSrvcUnitNbr(), claimDetails.getRevenueCode(),
				claimDetails.getRevenueDesc(), claimDetails.getAdmsnHourCode(),
				claimDetails.getAdmsnTypeCode(),
				claimDetails.getAdmsnSrvcCode(),
				claimDetails.getUnitOfService(),
				claimDetails.getChargedAmount(),
				claimDetails.getNonCovAmount(), userId });
		request.setCallFunction("init_claim_process");
		request.setSecureContext(userId);
		return request;
	}

	private static HyperledgerClient getClient(String context) {
		init();
		HyperledgerClient client = new HyperledgerClient(_hyperLedgerUrl,
				getUserId(context), _credentialMap.get(context).get("secret"));
		return client;
	}

	private static String getUserId(String context) {
		if (_credentialMap == null) {
			init();
		}
		return _credentialMap.get(context).get("uid");
	}

	private static void init() {
		if (_hyperLedgerUrl == null) {
			_hyperLedgerUrl = PropertyManager.getStringProperty(
					APP_PROPS_BUNDLE, HL_URL);
		}
		if (_chainCode == null) {
			_chainCode = PropertyManager.getStringProperty(APP_PROPS_BUNDLE,
					HL_CHAIN_CODE_ID);
		}
		if (_credentialMap == null) {
			_credentialMap = new HashMap<String, Map<String, String>>();
			String contexts = PropertyManager.getStringProperty(
					APP_PROPS_BUNDLE, HL_USER_CONTEXT);
			String userIds = PropertyManager.getStringProperty(
					APP_PROPS_BUNDLE, HL_USER_ID);
			String secrets = PropertyManager.getStringProperty(
					APP_PROPS_BUNDLE, HL_USER_SECRET);
			String[] secretList = secrets.split(",");
			String[] userList = userIds.split(",");
			String[] contextList = contexts.split(",");
			for (int index = 0; index < contextList.length; index++) {
				Map<String, String> contextMap = new HashMap<>();
				contextMap.put("uid", userList[index]);
				contextMap.put("secret", secretList[index]);
				_credentialMap.put(contextList[index], contextMap);
			}

		}
	}
}