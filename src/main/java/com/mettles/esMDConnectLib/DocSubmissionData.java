package com.mettles.esMDConnectLib;
import java.util.ArrayList;

public class DocSubmissionData {
	
	private String EsMDClaimID;
	public String getEsMDClaimID() {
		return EsMDClaimID;
	}
	public void setEsMDClaimID(String esMDClaimID) {
		EsMDClaimID = esMDClaimID;
	}
	public String getEsmdCaseId() {
		return EsmdCaseId;
	}
	public void setEsmdCaseId(String esmdCaseId) {
		EsmdCaseId = esmdCaseId;
	}
	public ReceipientInfo getIntendedRecepient() {
		return IntendedRecepient;
	}
	public void setIntendedRecepient(ReceipientInfo intendedRecepient) {
		IntendedRecepient = intendedRecepient;
	}
	public String getSplitStr() {
		return SplitStr;
	}
	public void setSplitStr(String splitStr) {
		SplitStr = splitStr;
	}
	private String EsmdCaseId;
	ReceipientInfo IntendedRecepient;
	private String SplitStr = "1-1";
	
	private String Title;
	

	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	private String AuthorType;
	public String getAuthorType() {
		return AuthorType;
	}
	public void setAuthorType(String authorType) {
		AuthorType = authorType;
	}
	public String getAuthorNPI() {
		return AuthorNPI;
	}
	public void setAuthorNPI(String authorNPI) {
		AuthorNPI = authorNPI;
	}
	public LoB getPurposeOfSubmission() {
		return PurposeOfSubmission;
	}
	public void setPurposeOfSubmission(LoB purposeOfSubmission) {
		PurposeOfSubmission = purposeOfSubmission;
	}
	private String AuthorNPI;
	
	private LoB PurposeOfSubmission;
	
	public ArrayList<DocumentPayload> getDocArryList() {
		return docArryList;
	}
	public void setDocArryList(ArrayList<DocumentPayload> docArryList) {
		this.docArryList = docArryList;
	}
	private ArrayList<DocumentPayload> docArryList;
	
	public HIHConfigurationParam getHihconfigvals() {
		return hihconfigvals;
	}
	public void setHihconfigvals(HIHConfigurationParam hihconfigvals) {
		this.hihconfigvals = hihconfigvals;
	}
	private HIHConfigurationParam hihconfigvals;
    
	

}
