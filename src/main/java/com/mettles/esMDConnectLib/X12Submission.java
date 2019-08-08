package com.mettles.esMDConnectLib;

public class X12Submission {
	
	public String getProviderNPI() {
		return providerNPI;
	}
	public void setProviderNPI(String providerNPI) {
		this.providerNPI = providerNPI;
	}
	public String gethIHOID() {
		return hIHOID;
	}
	public void sethIHOID(String hIHOID) {
		this.hIHOID = hIHOID;
	}
	public String getRcOid() {
		return rcOid;
	}
	public void setRcOid(String rcOid) {
		this.rcOid = rcOid;
	}
	public String getHihEDIid() {
		return hihEDIid;
	}
	public void setHihEDIid(String hihEDIid) {
		this.hihEDIid = hihEDIid;
	}
	public String getRcEDIid() {
		return rcEDIid;
	}
	public void setRcEDIid(String rcEDIid) {
		this.rcEDIid = rcEDIid;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	String providerNPI;
	String hIHOID;
	String rcOid;
	String hihEDIid;
	String rcEDIid;
	String issuer;
	
	public String getHih_Name() {
		return hih_Name;
	}
	public void setHih_Name(String hih_Name) {
		this.hih_Name = hih_Name;
	}
	public String getHih_Desp() {
		return hih_Desp;
	}
	public void setHih_Desp(String hih_Desp) {
		this.hih_Desp = hih_Desp;
	}
	String hih_Name;
	String hih_Desp;
	String esmd_Url;

	public String getEsmd_Url() {
		return esmd_Url;
	}
	public void setEsmd_Url(String esmd_Url) {
		this.esmd_Url = esmd_Url;
	}
	

}
