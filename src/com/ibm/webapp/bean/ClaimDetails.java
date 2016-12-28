package com.ibm.webapp.bean;

/**
 * Holds the claim details
 * @author SUDDUTT1
 *
 */
public class ClaimDetails {
	private String claimId;
	private String serviceDate;
	private String providerId;
	private String memberId;
	private String subscriberId;
	private String procedureCode;
	private String chargedAmount;
	private String approvedAmount;
	private String localPlanCode;
	private String remotePlanCode;
	private String costShare;
	private String adjustmentFlag;
	private String owner;
	private String finalApprovedAmount;
	private String paymentMethod;
	private String currentOwner;
	private ClaimStatus status;
	
	/**
	 * Constructor 
	 */
	public ClaimDetails()
	{
		super();
	}

	/**
	 * @return the claimId
	 */
	public String getClaimId() {
		return claimId;
	}

	/**
	 * @param claimId the claimId to set
	 */
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	/**
	 * @return the serviceDate
	 */
	public String getServiceDate() {
		return serviceDate;
	}

	/**
	 * @param serviceDate the serviceDate to set
	 */
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	/**
	 * @return the providerId
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the subscriberId
	 */
	public String getSubscriberId() {
		return subscriberId;
	}

	/**
	 * @param subscriberId the subscriberId to set
	 */
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	/**
	 * @return the procedureCode
	 */
	public String getProcedureCode() {
		return procedureCode;
	}

	/**
	 * @param procedureCode the procedureCode to set
	 */
	public void setProcedureCode(String procedureCode) {
		this.procedureCode = procedureCode;
	}

	/**
	 * @return the chargedAmount
	 */
	public String getChargedAmount() {
		return chargedAmount;
	}

	/**
	 * @param chargedAmount the chargedAmount to set
	 */
	public void setChargedAmount(String chargedAmount) {
		this.chargedAmount = chargedAmount;
	}

	/**
	 * @return the approvedAmount
	 */
	public String getApprovedAmount() {
		return approvedAmount;
	}

	/**
	 * @param approvedAmount the approvedAmount to set
	 */
	public void setApprovedAmount(String approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	/**
	 * @return the localPlanCode
	 */
	public String getLocalPlanCode() {
		return localPlanCode;
	}

	/**
	 * @param localPlanCode the localPlanCode to set
	 */
	public void setLocalPlanCode(String localPlanCode) {
		this.localPlanCode = localPlanCode;
	}

	/**
	 * @return the remotePlanCode
	 */
	public String getRemotePlanCode() {
		return remotePlanCode;
	}

	/**
	 * @param remotePlanCode the remotePlanCode to set
	 */
	public void setRemotePlanCode(String remotePlanCode) {
		this.remotePlanCode = remotePlanCode;
	}

	/**
	 * @return the costShare
	 */
	public String getCostShare() {
		return costShare;
	}

	/**
	 * @param costShare the costShare to set
	 */
	public void setCostShare(String costShare) {
		this.costShare = costShare;
	}

	/**
	 * @return the adjustmentFlag
	 */
	public String getAdjustmentFlag() {
		return adjustmentFlag;
	}

	/**
	 * @param adjustmentFlag the adjustmentFlag to set
	 */
	public void setAdjustmentFlag(String adjustmentFlag) {
		this.adjustmentFlag = adjustmentFlag;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the finalApprovedAmount
	 */
	public String getFinalApprovedAmount() {
		return finalApprovedAmount;
	}

	/**
	 * @param finalApprovedAmount the finalApprovedAmount to set
	 */
	public void setFinalApprovedAmount(String finalApprovedAmount) {
		this.finalApprovedAmount = finalApprovedAmount;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the currentOwner
	 */
	public String getCurrentOwner() {
		return currentOwner;
	}

	/**
	 * @param currentOwner the currentOwner to set
	 */
	public void setCurrentOwner(String currentOwner) {
		this.currentOwner = currentOwner;
	}

	/**
	 * @return the status
	 */
	public ClaimStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ClaimStatus status) {
		this.status = status;
	}
	
	
	
}
