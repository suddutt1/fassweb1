package com.ibm.webapp.bean;

import org.bson.Document;

import com.ibm.utils.MongoSerializable;

/**
 * Holds the claim details
 * @author SUDDUTT1
 *
 */
@SuppressWarnings("serial")
public class ClaimDetails extends MongoSerializable{
	
	private String claimId;
	private String serviceDate;
	private String admissionDate;
	private String providerId;
	private String memberId;
	private String subscriberId;
	private String diagCode;
	private String procedureCode;
	private String procedureDate;
	private String billCode;
	private String srvcUnitNbr;
	private String revenueCode;
	private String revenueDesc;
	private String admsnHourCode;
	private String admsnTypeCode;
	private String admsnSrvcCode;
	private String unitOfService;
	private String chargedAmount;
	private String nonCovAmount;
	private String approvedAmount;
	private String localPlanCode;
	private String remotePlanCode;
	private String costShare;
	private String adjustmentFlag;
	private String owner;
	private String finalApprovedAmount;
	private String paymentMethod;
	private String bcClaimStatus;
	//------Following attributes are for tracking purpose
	private String currentOwner;
	private ClaimStatus status;
	
	/**
	 * Constructor 
	 */
	public ClaimDetails()
	{
		super();
	}
	public String getClaimId(){
		return (String)get("claimId");
	}
	public String getServiceDate(){
		return (String)get("serviceDate");
	}
	public String getAdmissionDate(){
		return (String)get("admissionDate");
	}
	public String getProviderId(){
		return (String)get("providerId");
	}
	public String getMemberId(){
		return (String)get("memberId");
	}
	public String getSubscriberId(){
		return (String)get("subscriberId");
	}
	public String getDiagCode(){
		return (String)get("diagCode");
	}
	public String getProcedureCode(){
		return (String)get("procedureCode");
	}
	public String getProcedureDate(){
		return (String)get("procedureDate");
	}
	public String getBillCode(){
		return (String)get("billCode");
	}
	public String getSrvcUnitNbr(){
		return (String)get("srvcUnitNbr");
	}
	public String getRevenueCode(){
		return (String)get("revenueCode");
	}
	public String getRevenueDesc(){
		return (String)get("revenueDesc");
	}
	public String getAdmsnHourCode(){
		return (String)get("admsnHourCode");
	}
	public String getAdmsnTypeCode(){
		return (String)get("admsnTypeCode");
	}
	public String getAdmsnSrvcCode(){
		return (String)get("admsnSrvcCode");
	}
	public String getUnitOfService(){
		return (String)get("unitOfService");
	}
	public String getChargedAmount(){
		return (String)get("chargedAmount");
	}
	public String getNonCovAmount(){
		return (String)get("nonCovAmount");
	}
	public String getApprovedAmount(){
		return (String)get("approvedAmount");
	}
	public String getLocalPlanCode(){
		return (String)get("localPlanCode");
	}
	public String getRemotePlanCode(){
		return (String)get("remotePlanCode");
	}
	public String getCostShare(){
		return (String)get("costShare");
	}
	public String getAdjustmentFlag(){
		return (String)get("adjustmentFlag");
	}
	public String getOwner(){
		return (String)get("owner");
	}
	public String getFinalApprovedAmount(){
		return (String)get("finalApprovedAmount");
	}
	public String getPaymentMethod(){
		return (String)get("paymentMethod");
	}
	public String getCurrentOwner(){
		return (String)get("currentOwner");
	}
	public ClaimStatus getStatus(){
		return ClaimStatus.valueOf((String)get("status"));
	}
	/**
	 * @return the bcClaimStatus
	 */
	public String getBcClaimStatus() {
		return (String)get("bcClaimStatus");
		
	}
	public void setClaimId(String claimId){
		this.claimId=claimId;
		put("claimId",claimId);
	}
	public void setServiceDate(String serviceDate){
		this.serviceDate=serviceDate;
		put("serviceDate",serviceDate);
	}
	public void setAdmissionDate(String admissionDate){
		this.admissionDate=admissionDate;
		put("admissionDate",admissionDate);
	}
	public void setProviderId(String providerId){
		this.providerId=providerId;
		put("providerId",providerId);
	}
	public void setMemberId(String memberId){
		this.memberId=memberId;
		put("memberId",memberId);
	}
	public void setSubscriberId(String subscriberId){
		this.subscriberId=subscriberId;
		put("subscriberId",subscriberId);
	}
	public void setDiagCode(String diagCode){
		this.diagCode=diagCode;
		put("diagCode",diagCode);
	}
	public void setProcedureCode(String procedureCode){
		this.procedureCode=procedureCode;
		put("procedureCode",procedureCode);
	}
	public void setProcedureDate(String procedureDate){
		this.procedureDate=procedureDate;
		put("procedureDate",procedureDate);
	}
	public void setBillCode(String billCode){
		this.billCode=billCode;
		put("billCode",billCode);
	}
	public void setSrvcUnitNbr(String srvcUnitNbr){
		this.srvcUnitNbr=srvcUnitNbr;
		put("srvcUnitNbr",srvcUnitNbr);
	}
	public void setRevenueCode(String revenueCode){
		this.revenueCode=revenueCode;
		put("revenueCode",revenueCode);
	}
	public void setRevenueDesc(String revenueDesc){
		this.revenueDesc=revenueDesc;
		put("revenueDesc",revenueDesc);
	}
	public void setAdmsnHourCode(String admsnHourCode){
		this.admsnHourCode=admsnHourCode;
		put("admsnHourCode",admsnHourCode);
	}
	public void setAdmsnTypeCode(String admsnTypeCode){
		this.admsnTypeCode=admsnTypeCode;
		put("admsnTypeCode",admsnTypeCode);
	}
	public void setAdmsnSrvcCode(String admsnSrvcCode){
		this.admsnSrvcCode=admsnSrvcCode;
		put("admsnSrvcCode",admsnSrvcCode);
	}
	public void setUnitOfService(String unitOfService){
		this.unitOfService=unitOfService;
		put("unitOfService",unitOfService);
	}
	public void setChargedAmount(String chargedAmount){
		this.chargedAmount=chargedAmount;
		put("chargedAmount",chargedAmount);
	}
	public void setNonCovAmount(String nonCovAmount){
		this.nonCovAmount=nonCovAmount;
		put("nonCovAmount",nonCovAmount);
	}
	public void setApprovedAmount(String approvedAmount){
		this.approvedAmount=approvedAmount;
		put("approvedAmount",approvedAmount);
	}
	public void setLocalPlanCode(String localPlanCode){
		this.localPlanCode=localPlanCode;
		put("localPlanCode",localPlanCode);
	}
	public void setRemotePlanCode(String remotePlanCode){
		this.remotePlanCode=remotePlanCode;
		put("remotePlanCode",remotePlanCode);
	}
	public void setCostShare(String costShare){
		this.costShare=costShare;
		put("costShare",costShare);
	}
	public void setAdjustmentFlag(String adjustmentFlag){
		this.adjustmentFlag=adjustmentFlag;
		put("adjustmentFlag",adjustmentFlag);
	}
	public void setOwner(String owner){
		this.owner=owner;
		put("owner",owner);
	}
	public void setFinalApprovedAmount(String finalApprovedAmount){
		this.finalApprovedAmount=finalApprovedAmount;
		put("finalApprovedAmount",finalApprovedAmount);
	}
	public void setPaymentMethod(String paymentMethod){
		this.paymentMethod=paymentMethod;
		put("paymentMethod",paymentMethod);
	}
	public void setCurrentOwner(String currentOwner){
		this.currentOwner=currentOwner;
		put("currentOwner",currentOwner);
	}
	public void setStatus(ClaimStatus status){
		this.status=status;
		put("status",status.toString());
	}
	
	/**
	 * @param bcClaimStatus the bcClaimStatus to set
	 */
	public void setBcClaimStatus(String bcClaimStatus) {
		this.bcClaimStatus = bcClaimStatus;
		put("bcClaimStatus",bcClaimStatus);
	}

	public void buildInstance(Document doc){
		try{
		setInternalFields(doc);
		this.claimId=(String)doc.get("claimId");
		this.serviceDate=(String)doc.get("serviceDate");
		this.admissionDate=(String)doc.get("admissionDate");
		this.providerId=(String)doc.get("providerId");
		this.memberId=(String)doc.get("memberId");
		this.subscriberId=(String)doc.get("subscriberId");
		this.diagCode=(String)doc.get("diagCode");
		this.procedureCode=(String)doc.get("procedureCode");
		this.procedureDate=(String)doc.get("procedureDate");
		this.billCode=(String)doc.get("billCode");
		this.srvcUnitNbr=(String)doc.get("srvcUnitNbr");
		this.revenueCode=(String)doc.get("revenueCode");
		this.revenueDesc=(String)doc.get("revenueDesc");
		this.admsnHourCode=(String)doc.get("admsnHourCode");
		this.admsnTypeCode=(String)doc.get("admsnTypeCode");
		this.admsnSrvcCode=(String)doc.get("admsnSrvcCode");
		this.unitOfService=(String)doc.get("unitOfService");
		this.chargedAmount=(String)doc.get("chargedAmount");
		this.nonCovAmount=(String)doc.get("nonCovAmount");
		this.approvedAmount=(String)doc.get("approvedAmount");
		this.localPlanCode=(String)doc.get("localPlanCode");
		this.remotePlanCode=(String)doc.get("remotePlanCode");
		this.costShare=(String)doc.get("costShare");
		this.adjustmentFlag=(String)doc.get("adjustmentFlag");
		this.owner=(String)doc.get("owner");
		this.finalApprovedAmount=(String)doc.get("finalApprovedAmount");
		this.paymentMethod=(String)doc.get("paymentMethod");
		this.currentOwner=(String)doc.get("currentOwner");
		this.status=ClaimStatus.valueOf((String)doc.get("status"));
		this.bcClaimStatus=(String)doc.get("bcClaimStatus");
		}catch(Throwable ex)
		{
			ex.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClaimDetails [claimId=" + claimId + ", serviceDate="
				+ serviceDate + ", admissionDate=" + admissionDate
				+ ", providerId=" + providerId + ", memberId=" + memberId
				+ ", subscriberId=" + subscriberId + ", diagCode=" + diagCode
				+ ", procedureCode=" + procedureCode + ", procedureDate="
				+ procedureDate + ", billCode=" + billCode + ", srvcUnitNbr="
				+ srvcUnitNbr + ", revenueCode=" + revenueCode
				+ ", revenueDesc=" + revenueDesc + ", admsnHourCode="
				+ admsnHourCode + ", admsnTypeCode=" + admsnTypeCode
				+ ", admsnSrvcCode=" + admsnSrvcCode + ", unitOfService="
				+ unitOfService + ", chargedAmount=" + chargedAmount
				+ ", nonCovAmount=" + nonCovAmount + ", approvedAmount="
				+ approvedAmount + ", localPlanCode=" + localPlanCode
				+ ", remotePlanCode=" + remotePlanCode + ", costShare="
				+ costShare + ", adjustmentFlag=" + adjustmentFlag + ", owner="
				+ owner + ", finalApprovedAmount=" + finalApprovedAmount
				+ ", paymentMethod=" + paymentMethod + ", currentOwner="
				+ currentOwner + ", status=" + status +", + bcClaimStatus= "+ bcClaimStatus+"]";
	}
	
	
	
}
