package com.ibm.webapp.action;

public class ActionResponse {
	
	private int status;
	private long timeStamp;
	private Object result;
	private String actionError;
	
	/**
	 * @param stat
	 * @param rslt
	 */
	public ActionResponse(int stat,Object rslt)
	{
		this.timeStamp = System.currentTimeMillis();
		this.status = stat;
		this.result = rslt;
	}
	/**
	 * @param stat
	 * @param actionError
	 */
	public ActionResponse(int stat,String actionError)
	{
		this.timeStamp = System.currentTimeMillis();
		this.status = stat;
		this.actionError = actionError;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	/**
	 * @return the actionError
	 */
	public String getActionError() {
		return actionError;
	}
	/**
	 * @param actionError the actionError to set
	 */
	public void setActionError(String actionError) {
		this.actionError = actionError;
	}
	
	

}
