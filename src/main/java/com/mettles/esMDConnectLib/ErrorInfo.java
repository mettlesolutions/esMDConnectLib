package com.mettles.esMDConnectLib;

public class ErrorInfo {
	
	 
       
       public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getCodeContext() {
		return CodeContext;
	}
	public void setCodeContext(String codeContext) {
		CodeContext = codeContext;
	}
	public String getSeverity() {
		return Severity;
	}
	public void setSeverity(String severity) {
		Severity = severity;
	}
	private String ErrorCode;
       private String Value;
       private String CodeContext;
       private String Severity;
       
       

}
