package com.ibm.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;

import static com.ibm.webapp.action.ApplicationConstants.*;

/**
 * User login action view
 * @author SUDDUTT1
 *
 */
public class LoginAction implements WebActionHandler {

	@RequestMapping("login.wss")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObj = new ModelAndView(ViewType.GENERIC_NO_RENDER_VIEW,
				"text/html");

		String userId = request.getParameter("uid");
		String password = request.getParameter("password");
		String role = request.getParameter("userRole");
		if (validateLogin(userId, password, role)) {
			HttpSession session = request.getSession();
			session.setAttribute(APP_LOGIN_AUTH_TOKEN,
					System.currentTimeMillis() + "");
			session.setAttribute(APP_USER, userId);
			session.setAttribute(APP_USER_ROLE, role);
			if ("provider".equalsIgnoreCase(role)) {
				mvObj = new ModelAndView(ViewType.FORWARD_ACTION_VIEW);
				mvObj.setView("loadProvideHome.wss");
			} else if ("home".equalsIgnoreCase(role)) {
				mvObj = new ModelAndView(ViewType.FORWARD_ACTION_VIEW);
				mvObj.setView("loadHomeUserHome.wss");
			} else if ("host".equalsIgnoreCase(role)) {
				mvObj = new ModelAndView(ViewType.FORWARD_ACTION_VIEW);
				mvObj.setView("loadHostHome.wss");
			} else {
				session.removeAttribute(APP_LOGIN_AUTH_TOKEN);
				session.removeAttribute(APP_USER);
				session.removeAttribute(APP_USER_ROLE);
				mvObj.setView("login.html");
				mvObj.addModel(APP_ACTION_RESPONSE, new ActionResponse(
						ACTION_INVALID_INPUT, "Invalid user role"));
			}
		} else {
			mvObj.setView("login.html");
			mvObj.addModel(APP_ACTION_RESPONSE, new ActionResponse(
					ACTION_INVALID_INPUT, "Invalid user id/password"));
		}
		return mvObj;
	}

	@RequestMapping("home.wss")
	public ModelAndView home(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObj = new ModelAndView(ViewType.GENERIC_NO_RENDER_VIEW,
				"text/html");
		mvObj.setView("app/home.html");

		return mvObj;
	}

	@RequestMapping("logout.wss")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().invalidate();
		ModelAndView mvObj = new ModelAndView(ViewType.GENERIC_NO_RENDER_VIEW,
				"text/html");
		mvObj.setView("index.html");
		return mvObj;
	}

	private boolean validateLogin(String userId, String password, String role) {
		boolean isValid = false;
		// TODO:
		if (password.equals("password")) {
			isValid = true;
		}
		return isValid;
	}
}
