package com.mettles.esMDConnectLib;

public class StatusInfo {
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getEsmdTransactionId() {
		return esmdTransactionId;
	}
	public void setEsmdTransactionId(String esmdTransactionId) {
		this.esmdTransactionId = esmdTransactionId;
	}
	private String status;
	private String result;
	private String esmdTransactionId;
	

}
