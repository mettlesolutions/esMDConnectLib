package com.mettles.esMDConnectLib;

public class LoB {
	
	public String getPurposeOfSubmission() {
		return PurposeOfSubmission;
	}
	public void setPurposeOfSubmission(String purposeOfSubmission) {
		PurposeOfSubmission = purposeOfSubmission;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Boolean getIsEsmdClaimIdMandatory() {
		return isEsmdClaimIdMandatory;
	}
	public void setIsEsmdClaimIdMandatory(Boolean isEsmdClaimIdMandatory) {
		this.isEsmdClaimIdMandatory = isEsmdClaimIdMandatory;
	}
	public Boolean getIsEsmdClaimDisplayed() {
		return isEsmdClaimDisplayed;
	}
	public void setIsEsmdClaimDisplayed(Boolean isEsmdClaimDisplayed) {
		this.isEsmdClaimDisplayed = isEsmdClaimDisplayed;
	}
	public Boolean getIsCaseIdDisplayed() {
		return isCaseIdDisplayed;
	}
	public void setIsCaseIdDisplayed(Boolean isCaseIdDisplayed) {
		this.isCaseIdDisplayed = isCaseIdDisplayed;
	}
	private String PurposeOfSubmission;
	private String contentType;
	private Boolean isEsmdClaimIdMandatory;
	private Boolean isEsmdClaimDisplayed;
	private Boolean isCaseIdDisplayed;
	
	
	
	

}
