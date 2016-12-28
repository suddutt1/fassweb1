package com.ibm.webapp.action;

import static com.ibm.webapp.action.ApplicationConstants.ACTION_SUCESS;
import static com.ibm.webapp.action.ApplicationConstants.APP_ACTION_RESPONSE;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.webapp.bean.ClaimDetails;
import com.ibm.webapp.dao.ClaimDAO;

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
				ACTION_SUCESS,getClaimDetails(claimId) ));
		return mvObject;

	}

	private ClaimDetails getClaimDetails(String claimid) {
		return ClaimDAO.getClaimDetailsForProvider(claimid);
	}

}