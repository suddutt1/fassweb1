package com.ibm.webapp.bean;

import org.bson.Document;

import com.ibm.utils.MongoSerializable;

@SuppressWarnings("serial")
public class BenefitSnapShot extends MongoSerializable{
	
	private String claimId;
	private String year;
	private String description;
	private String benefitAmt ;
	private String amtYTD;
	private String balanceAmount;
	
	/**
	 * @param objType
	 * @param claimId
	 * @param year
	 * @param description
	 * @param benefitAmt
	 * @param amtYTD
	 * @param balanceAmount
	 */
	public BenefitSnapShot(String claimId, String year,
			String description, String benefitAmt, String amtYTD,
			String balanceAmount) {
		super();
		setClaimId(claimId);
		setYear(year);
		setDescription(description);
		setBenefitAmt(benefitAmt);
		setAmtYTD(amtYTD);
		setBalanceAmount(balanceAmount);
	}
	public BenefitSnapShot()
	{
		super();
	}
	public String getClaimId(){
		return (String)get("claimId");
	}
	public String getYear(){
		return (String)get("year");
	}
	public String getDescription(){
		return (String)get("description");
	}
	public String getBenefitAmt(){
		return (String)get("benefitAmt");
	}
	public String getAmtYTD(){
		return (String)get("amtYTD");
	}
	public String getBalanceAmount(){
		return (String)get("balanceAmount");
	}
	public void setClaimId(String claimId){
		this.claimId=claimId;
		put("claimId",claimId);
	}
	public void setYear(String year){
		this.year=year;
		put("year",year);
	}
	public void setDescription(String description){
		this.description=description;
		put("description",description);
	}
	public void setBenefitAmt(String benefitAmt){
		this.benefitAmt=benefitAmt;
		put("benefitAmt",benefitAmt);
	}
	public void setAmtYTD(String amtYTD){
		this.amtYTD=amtYTD;
		put("amtYTD",amtYTD);
	}
	public void setBalanceAmount(String balanceAmount){
		this.balanceAmount=balanceAmount;
		put("balanceAmount",balanceAmount);
	}
	public void  buildInstance(Document doc){
		setInternalFields(doc);
		this.claimId=(String)doc.get("claimId");
		this.year=(String)doc.get("year");
		this.description=(String)doc.get("description");
		this.benefitAmt=(String)doc.get("benefitAmt");
		this.amtYTD=(String)doc.get("amtYTD");
		this.balanceAmount=(String)doc.get("balanceAmount");
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BenefitSnapShot [claimId=" + claimId + ", year=" + year
				+ ", description=" + description + ", benefitAmt=" + benefitAmt
				+ ", amtYTD=" + amtYTD + ", balanceAmount=" + balanceAmount
				+ "]";
	}

}
