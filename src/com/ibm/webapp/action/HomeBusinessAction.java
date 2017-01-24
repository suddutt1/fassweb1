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

		ClaimStatus status = ClaimStatus
				.valueOf(request.getParameter("status"));
		ClaimDetails claimDetails = getClaimDetails(claimId);
		if (claimDetails != null) {
			claimDetails.setStatus(status);
			claimDetails.setCurrentOwner(CLAIM_OWNER_HOST);
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

	@RequestMapping("adjudicateClaimByHome.wss")
	public ModelAndView adjudicateClaimByHome(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		ActionResponse actionResponse = null;
		String claimId = request.getParameter("claimId");
		ClaimDetails claimDetails = getClaimDetails(claimId);
		if (claimDetails != null) {
			// Generating a random approval amt
			adjudicate(claimDetails);
			// Update the claim details
			if (ClaimDAO.updateClaim(claimDetails)) {
				HyperLedgerResponse resp = ClaimHLDAO
						.adjudicateClaim(claimId,
								claimDetails.getFinalApprovedAmount(), "455",
								"060",claimDetails.getPaitentLiability(),claimDetails.getCostShare());
				if (resp.isOk()) {
					actionResponse = new ActionResponse(ACTION_SUCESS,
							claimDetails);
				} else {
					actionResponse = new ActionResponse(ACTION_ERROR,
							"Hyperledger transaction for adjucation failed");
				}
			} else {
				actionResponse = new ActionResponse(ACTION_ERROR,
						"Claim adjudication failed ");
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

	private void adjudicate(ClaimDetails claimDetails) {
		int approvedAmt = getAmount(claimDetails.getApprovedAmount());
		int nonCovAmt = getAmount(claimDetails.getNonCovAmount());
		int percentage = ((System.currentTimeMillis() % 2) == 0 ? 20 : 40);

		int finalApprovedAmt = (int) ((approvedAmt - nonCovAmt) * 1.0
				* percentage / 100);
		claimDetails.setFinalApprovedAmount(String.valueOf(finalApprovedAmt)
				+ ".00");
		claimDetails.setCostShare(String.valueOf(100 - percentage) + ":"
				+ String.valueOf(percentage));
		claimDetails.setPaitentLiability(String.valueOf(approvedAmt-finalApprovedAmt)+".00");

	}

	private int getAmount(String amt) {
		double returnAmt = 0.0;
		try {
			returnAmt = Float.parseFloat(amt.trim());

		} catch (Exception ex) {
			returnAmt = 0.0;
		}
		return (int) returnAmt;
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
