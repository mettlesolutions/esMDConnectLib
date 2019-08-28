package com.mettles.esMDConnectLib;

public class X12SubmissionStatus {
  String responsePayload;
  public String getResponsePayload() {
	return responsePayload;
}
public void setResponsePayload(String responsePayload) {
	this.responsePayload = responsePayload;
}
public String getError_message() {
	return error_message;
}
public void setError_message(String error_message) {
	this.error_message = error_message;
}
public String getPayloadType() {
	return payloadType;
}
public void setPayloadType(String payloadType) {
	this.payloadType = payloadType;
}
public String getError_code() {
	return error_code;
}
public void setError_code(String error_code) {
	this.error_code = error_code;
}
public String getPayloadID() {
	return payloadID;
}
public void setPayloadID(String payloadID) {
	this.payloadID = payloadID;
}
String error_message;
  String payloadType;
  String error_code;
  String payloadID;
}
