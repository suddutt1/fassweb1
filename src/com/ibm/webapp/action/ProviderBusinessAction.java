package com.ibm.webapp.action;

import static com.ibm.webapp.action.ApplicationConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Action class to handle provider originated web page actions
 * 
 * @author SUDDUTT1
 *
 */
public class ProviderBusinessAction implements WebActionHandler {

	
	@RequestMapping("loadProvideHome.wss")
	public ModelAndView loadProvideHome(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(
				ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		mvObject.setView("app/providerHome.html");
		mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
				ACTION_SUCESS, ClaimDAO.getClaimListForProvider()));
		return mvObject;
	}

	@RequestMapping("viewProviderClaimDetails.wss")
	public ModelAndView viewProviderClaimDetails(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(
				ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		mvObject.setView("app/providerClaimDetails.html");
		// Assuming a happy flow
		String claimId = request.getParameter("claimId");
		mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
				ACTION_SUCESS, getClaimDetails(claimId)));
		return mvObject;

	}

	/**
	 * Claim update action by a provider. This is an ajax call
	 * 
	 * @param request
	 * @param response
	 * @return Ajax response
	 */
	@RequestMapping("saveClaimToHyperLedger.wss")
	public ModelAndView saveClaimToHyperLedger(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		ActionResponse actionResponse = null;
		String claimId = request.getParameter("claimId");
		ClaimDetails claimDetails = getClaimDetails(claimId);
		if (claimDetails != null) {
			HyperLedgerResponse resp = ClaimHLDAO
					.initiaClaimProcess(claimDetails);
			if (resp.isOk()) {
				claimDetails.setStatus(ClaimStatus.SAVED_TO_BLOCK_CHAIN);
				ClaimDAO.updateClaim(claimDetails);
				actionResponse = new ActionResponse(ACTION_SUCESS, claimDetails);
			} else {
				actionResponse = new ActionResponse(ACTION_ERROR,
						"Hyperledger entry could not be intiated");
			}
		} else {
			actionResponse = new ActionResponse(ACTION_INVALID_INPUT,
					"Invalid claim numebr/claim not found");
		}
		mvObject.setView(CommonUtil.toJson(actionResponse));
		return mvObject;
	}

	/**
	 * Claim update action by a provider. This is an ajax call
	 * 
	 * @param request
	 * @param response
	 * @return Ajax response
	 */
	@RequestMapping("updateClaimStatusProvider.wss")
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
						.sendClaimToHome(claimDetails.getClaimId());
				if (resp.isOk()) {
					actionResponse = new ActionResponse(ACTION_SUCESS,
							claimDetails);
				} else {
					claimDetails.setStatus(ClaimStatus.ERROR_INIITIATOR);
					claimDetails.setCurrentOwner(CLAIM_OWNER_PROVIER);
					// FALL BACK
					ClaimDAO.updateClaim(claimDetails);
					actionResponse = new ActionResponse(ACTION_ERROR,
							"Claim could not be sent to host");
				}

			}
		} else {
			actionResponse = new ActionResponse(ACTION_INVALID_INPUT,
					"Invalid claim numebr/claim not found");
		}
		mvObject.setView(CommonUtil.toJson(actionResponse));
		return mvObject;
	}

	private ClaimDetails getClaimDetails(String claimid) {
		return ClaimDAO.getClaimDetailsForProvider(claimid);
	}

}