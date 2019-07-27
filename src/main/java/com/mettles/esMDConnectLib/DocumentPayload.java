package com.mettles.esMDConnectLib;

public class DocumentPayload {
	
	public String getBase64Payload() {
		return base64Payload;
	}
	public void setBase64Payload(String base64Payload) {
		this.base64Payload = base64Payload;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	private String base64Payload;
	private String filetype; //mime type
	private String language;
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
   
	private String AttachmentControlNumber;
	public String getAttachmentControlNumber() {
		return AttachmentControlNumber;
	}
	public void setAttachmentControlNumber(String attachmentControlNumber) {
		AttachmentControlNumber = attachmentControlNumber;
	}
	
	private String filesize; // payload size before base64 encoding
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
}
