package com.ibm.hyperledger.client;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.ibm.utils.HTTPRequester;
import com.ibm.utils.HTTPResponse;

public class HyperledgerClient {

	private static final Logger _LOGGER = Logger
			.getLogger(HyperledgerClient.class.getName());
	private static final String _HYPER_LEDGER_REGISTRATION = "/registrar";
	private static final String _HYPER_LEDGER_CHAINCODE = "/chaincode";

	private static final Gson _GSON_SERIALIZER = new GsonBuilder()
			.setPrettyPrinting().create();
	private static Type _MAP_TYPE = new TypeToken<Map<String, String>>() {
	}.getType();
	private static Type _MAP_GENERIC_TYPE = new TypeToken<Map<String, Object>>() {
	}.getType();

	private String vpUrl;
	private String user;
	private String userSecret;
	private boolean registered;
	private boolean registrationFailed;

	public HyperledgerClient(String validatingPairUrl, String userId,
			String secret) {
		this.vpUrl = validatingPairUrl;
		this.user = userId;
		this.userSecret = secret;

	}

	public boolean register() {

		try {
			if (!registered && !registrationFailed) {
				Map<String, String> input = new LinkedHashMap<>();
				input.put("enrollId", this.user);
				input.put("enrollSecret", this.userSecret);
				String postBody = _GSON_SERIALIZER.toJson(input);
				HTTPResponse response = HTTPRequester
						.sendPostRequest(this.vpUrl
								+ _HYPER_LEDGER_REGISTRATION, postBody, null);
				if (response.isOk()) {
					// System.out.println(response.getResult());
					Map<String, String> result = _GSON_SERIALIZER.fromJson(
							response.getResult(), _MAP_TYPE);
					if (result.containsKey("OK")) {
						this.registered = true;
					}
				} else {
					this.registered = false;
					this.registrationFailed = true;
				}

			}

		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "Failed to register with vp "
					+ this.vpUrl, ex);
			registered = false;
			registrationFailed = true;
		}

		return registered;
	}

	public HyperLedgerResponse invokeMethod(HyperLedgerRequest request) {
		HyperLedgerResponse hlResponse = null;
		try {

			if (register()) {
				String postBody = request.buildRequest();
				_LOGGER.info("Sending postBody " + postBody);
				HTTPResponse response = HTTPRequester.sendPostRequest(
						this.vpUrl + _HYPER_LEDGER_CHAINCODE, postBody, null);
				if (response.isOk()) {
					_LOGGER.info("Response " + response.getResult());
					Map<String, Object> result = _GSON_SERIALIZER.fromJson(
							response.getResult(), _MAP_GENERIC_TYPE);
					if (result.containsKey("result")) {
						Map<String, Object> resultDetails = (Map<String, Object>) result
								.get("result");
						_LOGGER.info("Response status "
								+ resultDetails.get("status"));
						_LOGGER.info("Response Message "
								+ resultDetails.get("message"));
						hlResponse = new HyperLedgerResponse(
								(String) resultDetails.get("status"));
						hlResponse.setMessage((String) resultDetails
								.get("message"));

					}
				} else {
					hlResponse = new HyperLedgerResponse(false);
					hlResponse.setMessage("Not registered");
				}

			}

		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING,
					"Failed to invoke with vp " + this.vpUrl, ex);
			hlResponse = new HyperLedgerResponse(false);
			hlResponse.setMessage("Exception thrown");
		}
		return hlResponse;
	}

	public HyperLedgerResponse deployChainCode(HyperLedgerRequest request) {

		HyperLedgerResponse hlResponse = null;
		try {

			if (register()) {
				String postBody = request.buildRequest();
				HTTPResponse response = HTTPRequester.sendPostRequest(
						this.vpUrl + _HYPER_LEDGER_CHAINCODE, postBody, null);
				if (response.isOk()) {
					_LOGGER.info("Response " + response.getResult());
					Map<String, Object> result = _GSON_SERIALIZER.fromJson(
							response.getResult(), _MAP_GENERIC_TYPE);
					if (result.containsKey("result")) {
						Map<String, Object> resultDetails = (Map<String, Object>) result
								.get("result");
						_LOGGER.info("Response status "
								+ resultDetails.get("status"));
						_LOGGER.info("Response Chain Code "
								+ resultDetails.get("message"));
						hlResponse = new HyperLedgerResponse(
								(String) resultDetails.get("status"));
						if (hlResponse.isOk()) {
							hlResponse.setChainCode((String) resultDetails
									.get("message"));
						}
					}
				} else {
					hlResponse = new HyperLedgerResponse(false);
					hlResponse.setMessage("Not registered");
				}

			}

		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING,
					"Failed to invoke with vp " + this.vpUrl, ex);
			hlResponse = new HyperLedgerResponse(false);
			hlResponse.setMessage("Exception thrown");

		}
		return hlResponse;

	}
}
