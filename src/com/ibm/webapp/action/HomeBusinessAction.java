package com.ibm.webapp.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.webapp.bean.ClaimDetails;

import static com.ibm.webapp.action.ApplicationConstants.*;

/**
 * Action class to handler Home Insurance Company originated web page actions
 * 
 * @author SUDDUTT1
 *
 */
public class HomeBusinessAction implements WebActionHandler {

	@RequestMapping("loadHomeUserHome.wss")
	public ModelAndView loadHomeUserHome(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.GENERIC_NO_RENDER_VIEW, "text/html");
		mvObject.setView("app/homeUser.html");
		mvObject.addModel(APP_ACTION_RESPONSE, new ActionResponse(ACTION_SUCESS, new ArrayList<ClaimDetails>()));
		return mvObject;

	}
}
