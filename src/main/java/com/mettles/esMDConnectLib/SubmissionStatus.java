package com.mettles.esMDConnectLib;

import java.util.ArrayList;

public class SubmissionStatus {
	private int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String message;
	public StatusInfo getStatchng() {
		return statchng;
	}
	public void setStatchng(StatusInfo statchng) {
		this.statchng = statchng;
	}
	public ArrayList<ErrorInfo> getError() {
		return error;
	}
	public void setError(ArrayList<ErrorInfo> error) {
		this.error = error;
	}
	private StatusInfo statchng;
	private ArrayList<ErrorInfo> error; 

}
