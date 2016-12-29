package com.ibm.webapp.action;

import static com.ibm.webapp.action.ApplicationConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.utils.CommonUtil;
import com.ibm.webapp.bean.ClaimDetails;
import com.ibm.webapp.bean.ClaimStatus;
import com.ibm.webapp.dao.ClaimDAO;


/**
 * Action class to handle Host insurance originated web page actions
 * 
 * @author SUDDUTT1
 *
 */
public class HostBusinessAction implements WebActionHandler {

	@RequestMapping("loadHostHome.wss")
	public ModelAndView loadHostHome(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mvObject = new ModelAndView(
				ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		mvObject.setView("app/hostHome.html");
		/*mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
				ACTION_SUCESS, ClaimDAO.getClaimListForProvider()));*/
		return mvObject;
	}
	@RequestMapping("loadClaims.wss")
	public ModelAndView loadClaims(HttpServletRequest request,HttpServletResponse response)
	{
		ModelAndView mvObject = new ModelAndView(
				ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		ClaimStatus statusToView = ClaimStatus.valueOf(request.getParameter("status"));
		if(statusToView == ClaimStatus.PROVIDER_SENT_TO_HOST)
		{
			mvObject.setView("app/hostClaimListFromProvider.html");
			mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
				ACTION_SUCESS, ClaimDAO.getClaimListForHost(statusToView)));
		}
		else if(statusToView == ClaimStatus.HOME_SENT_TO_HOST)
		{
			mvObject.setView("app/hostClaimListFromHome.html");
			/*mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
					ACTION_SUCESS, ClaimDAO.getClaimListForProvider()));*/
		}
		else
		{
			//We really do not know ??? :) 
		}
		return mvObject;
	}
	@RequestMapping("viewClaimDetailsByHostFromProvider.wss")
	public ModelAndView viewClaimDetailsByHostFromProvider(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(
				ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		mvObject.setView("app/providerSentClaimDetails.html");
		// Assuming a happy flow
		String claimId = request.getParameter("claimId");
			mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(
				ACTION_SUCESS, getClaimDetails(claimId)));
		return mvObject;

	}
	@RequestMapping("updateClaimStatusHost.wss")
	public ModelAndView updateClaim(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		ActionResponse actionResponse = null;
		String claimId = request.getParameter("claimId");
		ClaimStatus status = ClaimStatus
				.valueOf(request.getParameter("status"));
		ClaimDetails claimDetails = getClaimDetails(claimId);
		if (claimDetails != null) {
			claimDetails.setStatus(status);
			if(status.equals(ClaimStatus.HOST_SENT_TO_HOME))
			{
				claimDetails.setCurrentOwner(CLAIM_OWNER_HOME);
			}
			else if(status.equals(ClaimStatus.HOST_SENT_TO_CFA))
			{
				claimDetails.setCurrentOwner(CLAIM_OWNER_CFA);
			}	
			if (!ClaimDAO.updateClaim(claimDetails)) {
				actionResponse = new ActionResponse(ACTION_ERROR,
						"Claim could not be sent");
			} else {
				actionResponse = new ActionResponse(ACTION_SUCESS, claimDetails);
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