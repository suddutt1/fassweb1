package com.ibm.webapp.action;

import static com.ibm.webapp.action.ApplicationConstants.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.utils.CommonUtil;
import com.ibm.webapp.bean.BenefitSnapShot;
import com.ibm.webapp.bean.ClaimDetails;
import com.ibm.webapp.bean.EOBHeader;
import com.ibm.webapp.dao.ClaimDAO;

public class EOBAction implements WebActionHandler {

	private static final Random _RANDOM_GEN = new Random(
			System.currentTimeMillis());
	private static final SimpleDateFormat _DT_FORMATTER = new SimpleDateFormat(
			"MM/dd/yyyy");

	@RequestMapping("generateEOB.wss")
	public ModelAndView generateEOB(HttpServletRequest request,
			HttpServletResponse response) {
		ActionResponse actionResponse = null;
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		String claimId = request.getParameter("claimId");
		ClaimDetails claimDetails = getClaimDetails(claimId);
		if (ClaimDAO.getEODHeader(claimId) == null) {
			if (generateEOB(claimDetails)) {
				PushnotificationHanlder.sendMessage("{ \"claimId\": \""
						+ claimId + "\" }");
				actionResponse = new ActionResponse(ACTION_SUCESS, claimDetails);
			} else {
				actionResponse = new ActionResponse(ACTION_ERROR,
						"Not able to generate EOB data");
			}
		} else { // It is already there .. No need to regenerate
			PushnotificationHanlder.sendMessage("{ \"claimId\": \""
					+ claimId + "\" }");
			actionResponse = new ActionResponse(ACTION_SUCESS, claimDetails);
		}
		mvObject.setView(CommonUtil.toJson(actionResponse));
		return mvObject;
	}

	@RequestMapping("viewEOB.wss")
	public ModelAndView viewEOB(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mvObject = new ModelAndView(ViewType.JSP_VIEW);
		String claimId = request.getParameter("claimId");
		mvObject.addModel("claim", getClaimDetails(claimId));
		mvObject.addModel("eobHeader", ClaimDAO.getEODHeader(claimId));
		mvObject.addModel("benefitList",
				ClaimDAO.getBenefitSnapShotList(claimId));
		mvObject.setView("app/eobTemplate.jsp");
		return mvObject;
	}

	private boolean generateEOB(ClaimDetails claimDetails) {
		EOBHeader header = new EOBHeader();
		header.setClaimId(claimDetails.getClaimId());
		header.setPatient("XXXX YYYYY");
		header.setPatientAccount("XXXX YYYYY ZZZZ");
		header.setInsuredID("XXXXXX");
		header.setProviderId(claimDetails.getProviderId());
		header.setProviderStatus("OUT OF NETWORK");
		header.setEobDate(_DT_FORMATTER.format(new Date()));
		header.setAmtByProvider(claimDetails.getNonCovAmount());
		return (ClaimDAO.saveEOBHeader(header) && ClaimDAO
				.saveBenefitSnapshot(benefitGenerator(claimDetails.getClaimId())));

	}

	public static List<BenefitSnapShot> benefitGenerator(String cliamId) {
		List<BenefitSnapShot> list = new ArrayList<>();
		String[] lineItems = new String[] {
				"INDIVIDUAL IN-NETWORK DEDUCTIBLE ",
				"INDIVIDUAL OUT-OF-NETWORK DEDUCTIBLE",
				"FAMILY IN-NETWORK DEDUCTIBLE",
				"FAMILY OUT-OF-NETWORK DEDUCTIBLE",
				"INDIVIDUAL IN-NETWORK OUT-OF-POCKET-LIMIT",
				"INDIVIDUAL OUT-OF-NETWORK OUT-OF-POCKET-LIMIT",
				"FAMILY IN-NETWORK OUT-OF-POCKET-LIMIT",
				"FAMILY OUT-OF-NETWORK OUT-OF POCKET-LIMIT" };
		for (int index = 0; index < lineItems.length; index++) {
			int amt = generateNumber(1800);
			int amtYtd = generateNumber(amt);
			int balance = amt - amtYtd;
			BenefitSnapShot snap = new BenefitSnapShot(cliamId, "2016",
					lineItems[index], amt + ".00", amtYtd + ".00", balance
							+ ".00");
			list.add(snap);
		}
		return list;

	}

	private ClaimDetails getClaimDetails(String claimId) {
		return ClaimDAO.getClaimDetailsForProvider(claimId);
	}

	public static int generateNumber(int max) {
		return _RANDOM_GEN.nextInt(max) + 1;
	}
}
