package com.ibm.hyperledger.client;

import static com.ibm.webapp.action.ApplicationConstants.ACTION_SUCESS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.app.web.frmwk.WebActionHandler;
import com.ibm.app.web.frmwk.annotations.RequestMapping;
import com.ibm.app.web.frmwk.bean.ModelAndView;
import com.ibm.app.web.frmwk.bean.ViewType;
import com.ibm.utils.CommonUtil;
import com.ibm.webapp.action.ActionResponse;
import com.ibm.webapp.hldao.ClaimHLDAO;

public class HyperLedgerStatusAction implements WebActionHandler {
	
	@RequestMapping("viewHLStat.wss")
	public ModelAndView getHyperLedgerStatus(HttpServletRequest request,HttpServletResponse response)
	{
		String message = "{\"Message\":\"No data found\" }";
		ModelAndView mvObject = new ModelAndView(ViewType.AJAX_VIEW);
		ActionResponse actionResponse = null;
		HyperLedgerResponse hlResp = ClaimHLDAO.getStatistics(null);
		if(hlResp.isOk())
		{
			message= hlResp.getMessage();
		}
		actionResponse = new ActionResponse(ACTION_SUCESS,
					message,null);
		
		mvObject.setView(CommonUtil.toJson(actionResponse));
		return mvObject;
	}

}
