package com.ibm.webapp.bean;

import org.bson.Document;

import com.ibm.utils.MongoSerializable;

@SuppressWarnings("serial")
public class EOBHeader extends MongoSerializable{
	
	private String patient;
	private String patientAccount;
	private String insuredID;
	private String providerId;
	private String claimId;
	private String providerStatus;
	private String eobDate;
	private String amtByProvider;
	
	public EOBHeader() {
		super();
		
	}
	
	public String getPatient(){
		return (String)get("patient");
	}
	public String getPatientAccount(){
		return (String)get("patientAccount");
	}
	public String getInsuredID(){
		return (String)get("insuredID");
	}
	public String getProviderId(){
		return (String)get("providerId");
	}
	public String getClaimId(){
		return (String)get("claimId");
	}
	public String getProviderStatus(){
		return (String)get("providerStatus");
	}
	public String getEobDate(){
		return (String)get("eobDate");
	}
	public String getAmtByProvider(){
		return (String)get("amtByProvider");
	}
	public void setPatient(String patient){
		this.patient=patient;
		put("patient",patient);
	}
	public void setPatientAccount(String patientAccount){
		this.patientAccount=patientAccount;
		put("patientAccount",patientAccount);
	}
	public void setInsuredID(String insuredID){
		this.insuredID=insuredID;
		put("insuredID",insuredID);
	}
	public void setProviderId(String providerId){
		this.providerId=providerId;
		put("providerId",providerId);
	}
	public void setClaimId(String claimId){
		this.claimId=claimId;
		put("claimId",claimId);
	}
	public void setProviderStatus(String providerStatus){
		this.providerStatus=providerStatus;
		put("providerStatus",providerStatus);
	}
	public void setEobDate(String eobDate){
		this.eobDate=eobDate;
		put("eobDate",eobDate);
	}
	public void setAmtByProvider(String amtByProvider){
		this.amtByProvider=amtByProvider;
		put("amtByProvider",amtByProvider);
	}
	public void  buildInstance(Document doc){
		setInternalFields(doc);
		this.patient=(String)doc.get("patient");
		this.patientAccount=(String)doc.get("patientAccount");
		this.insuredID=(String)doc.get("insuredID");
		this.providerId=(String)doc.get("providerId");
		this.claimId=(String)doc.get("claimId");
		this.providerStatus=(String)doc.get("providerStatus");
		this.eobDate=(String)doc.get("eobDate");
		this.amtByProvider=(String)doc.get("amtByProvider");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EOBHeader [patient=" + patient + ", patientAccount="
				+ patientAccount + ", insuredID=" + insuredID + ", providerId="
				+ providerId + ", claimId=" + claimId + ", providerStatus="
				+ providerStatus + ", eobDate=" + eobDate + ", amtByProvider="
				+ amtByProvider + "]";
	}


}
