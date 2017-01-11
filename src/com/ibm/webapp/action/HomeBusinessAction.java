package com.ibm.webapp.action;

import static com.ibm.webapp.action.ApplicationConstants.ACTION_ERROR;
import static com.ibm.webapp.action.ApplicationConstants.ACTION_INVALID_INPUT;
import static com.ibm.webapp.action.ApplicationConstants.ACTION_SUCESS;
import static com.ibm.webapp.action.ApplicationConstants.APP_ACTION_RESPONSE;
import static com.ibm.webapp.action.ApplicationConstants.CLAIM_OWNER_HOST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.hyperledger.client.HyperLedgerResponse;
import com.ibm.utils.CommonUtil;
import com.ibm.webapp.bean.ClaimDetails;
import com.ibm.webapp.bean.ClaimStatus;
import com.ibm.webapp.dao.ClaimDAO;
import com.ibm.webapp.hldao.ClaimHLDAO;

/**
 * Action class to handler Home Insurance Company originated web page actions
 * 
 * @author SUDDUTT1
 *
 */
public class HomeBusinessAction implements WebActionHandler {

	private static final Gson _GSON_DESERIALIZER = new GsonBuilder().create();

	@RequestMapping("loadHomeUserHome.wss")
	public ModelAndView loadHomeUserHome(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(
				ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		mvObject.setView("app/homeUser.html");
		mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
				ACTION_SUCESS, ClaimDAO.getClaimListForHome()));
		return mvObject;

	}

	/**
	 * Claim update action by a provider. This is an ajax call
	 * 
	 * @param request
	 * @param response
	 * @return Ajax response
	 */
	@RequestMapping("updateClaimStatusHome.wss")
	public ModelAndView updateClaimStatus(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		ActionResponse actionResponse = null;
		String claimId = request.getParameter("claimId");
		String finalApvlAmt = request.getParameter("finalApprovedAmount");
		ClaimStatus status = ClaimStatus
				.valueOf(request.getParameter("status"));
		ClaimDetails claimDetails = getClaimDetails(claimId);
		if (claimDetails != null && isValidAmount(finalApvlAmt)) {
			claimDetails.setStatus(status);
			claimDetails.setCurrentOwner(CLAIM_OWNER_HOST);
			claimDetails.setFinalApprovedAmount(finalApvlAmt.trim());
			if (!ClaimDAO.updateClaim(claimDetails)) {
				actionResponse = new ActionResponse(ACTION_ERROR,
						"Claim could not be sent to Host");
			} else {
				HyperLedgerResponse resp = ClaimHLDAO
						.sendClaimToHostByHome(claimId);
				if (resp.isOk()) {
					actionResponse = new ActionResponse(ACTION_SUCESS,
							claimDetails);
				} else {
					actionResponse = new ActionResponse(ACTION_ERROR,
							"Hyperledger transaction failed");
				}
			}
		} else {
			actionResponse = new ActionResponse(ACTION_INVALID_INPUT,
					"Invalid claim numebr/claim not found");
		}
		mvObject.setView(CommonUtil.toJson(actionResponse));
		return mvObject;
	}

	@RequestMapping("viewClaimDetailsByHomeFromHost.wss")
	public ModelAndView viewClaimDetailsByHomeFromHost(
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(
				ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		mvObject.setView("app/hostSentClaimDetails.html");
		// Assuming a happy flow
		String claimId = request.getParameter("claimId");
		mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
				ACTION_SUCESS, getClaimDetails(claimId)));
		return mvObject;

	}

	private ClaimDetails getClaimDetails(String claimid) {
		// Read from BlockChain
		HyperLedgerResponse resp = ClaimHLDAO.getClaimDetailsForHost(claimid);
		if (resp.isOk()) {
			ClaimDetails claimDetails = _GSON_DESERIALIZER.fromJson(
					resp.getMessage(), ClaimDetails.class);
			claimDetails
					.setAdmsnTypeCode(claimDetails.getAdmsnTypeCode() + ".");
			return claimDetails;
		}
		return ClaimDAO.getClaimDetailsForProvider(claimid);
	}

	private boolean isValidAmount(String amt) {
		boolean isValid = false;
		try {
			Float.parseFloat(amt.trim());
			isValid = true;
		} catch (Exception ex) {
			isValid = false;
		}
		return isValid;
	}
}
