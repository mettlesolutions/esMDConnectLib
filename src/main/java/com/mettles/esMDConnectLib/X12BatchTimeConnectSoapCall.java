package com.mettles.esMDConnectLib;

import gov.hhs.fha.nhinc.adaptercore.AdapterCOREGenericBatchTransactionPortType;
import gov.hhs.fha.nhinc.corex12.ds.genericbatch.common.adapter.proxy.service.AdapterX12BatchUnsecuredServicePortDescriptor;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthnStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceAssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceConditionsType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterBatchSubmissionRequestType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterBatchSubmissionResponseType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterCOREEnvelopeRealTimeResponseType;
import gov.hhs.fha.nhinc.corex12.ds.realtime.adapter.proxy.service.AdapterX12RealTimeUnsecuredServicePortDescriptor;
import gov.hhs.fha.nhinc.messaging.client.CONNECTCXFClientFactory;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;

import java.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeBatchSubmission;


public class X12BatchTimeConnectSoapCall {
	
	public static String CORERuleVersion = "2.2.0";
	public static String ProcessingMode = "Batch";
	public static String PayloadType = "X12_275_Request_006020X316";
	public static String action = "Response to Additional Documentation Request (ADR)";
	
public int BatchConnectSoapClient(X12Submission x12sub, String uniquestr, String ediPayload) {
	String url = "http://valtest.mettles.com:8080/Adapter/CORE_X12DocumentSubmission/A_0/COREGenericBatch/AdapterCOREGenericBatchRequestUnsecured";
	int status = 0;
	AdapterBatchSubmissionRequestType bacthreq = new AdapterBatchSubmissionRequestType();
	AdapterBatchSubmissionResponseType response = null;
	COREEnvelopeBatchSubmission coreenvsub = new COREEnvelopeBatchSubmission();
	coreenvsub.setProcessingMode(ProcessingMode);
	coreenvsub.setPayloadType(PayloadType);
	coreenvsub.setCORERuleVersion(CORERuleVersion);
	
	String dtcurrent = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
	coreenvsub.setTimeStamp(dtcurrent+'Z');
	coreenvsub.setSenderID("urn:oid:"+x12sub.gethIHOID());
	coreenvsub.setReceiverID("urn:oid:"+x12sub.getRcOid());
	
	String payloadID = uniquestr.replaceAll("-", "");
	coreenvsub.setPayloadID(payloadID);
	String b64edipayload = Base64.getEncoder().encodeToString(ediPayload.getBytes());
	try {
	coreenvsub.setCheckSum(sha1(b64edipayload));
	DataSource ds = new ByteArrayDataSource(ediPayload, "text/plain; charset=UTF-8");
	DataHandler handler = new DataHandler(ds);
	coreenvsub.setPayload(handler);
	}catch(Exception e) {
		e.printStackTrace();
	}
	coreenvsub.setPayloadLength(b64edipayload.length());
	AssertionType asser = new AssertionType();
	asser.setNationalProviderId(x12sub.getProviderNPI());
	asser.setUserInfo(populateUserInfo(x12sub));
	asser.getUniquePatientId().add("urn:oid:"+x12sub.getRcOid());
	  HomeCommunityType hc = new HomeCommunityType();
        hc.setName(x12sub.getHih_Name());
        hc.setDescription(x12sub.getHih_Desp());
        hc.setHomeCommunityId(x12sub.gethIHOID());
        asser.setHomeCommunity(hc);
        asser.setPurposeOfDisclosureCoded(populatePurpOfDisc());
        asser.setSamlAuthnStatement(populateSamlAuthnStatementType());
       
        asser.setSamlAuthzDecisionStatement( populateSamlAuthzDecStmnt(x12sub, uniquestr));
       
        asser.setMessageId("urn:oid:"+UUID.randomUUID().toString());
        bacthreq.setAssertion(asser);
	
	
	bacthreq.setCOREEnvelopeBatchSubmission(coreenvsub);
	
	ServicePortDescriptor<AdapterCOREGenericBatchTransactionPortType> coreport = new AdapterX12BatchUnsecuredServicePortDescriptor();
	
	 CONNECTClient<AdapterCOREGenericBatchTransactionPortType> client = getCONNECTClientUnsecured(coreport,url,asser);
	   try {
		   response = (AdapterBatchSubmissionResponseType) client.invokePort(AdapterCOREGenericBatchTransactionPortType.class,"batchSubmitTransaction", bacthreq);
	   }catch(Exception e) {
		   
	   }
	   if(response != null) {
		   System.out.println("payload is"+response.getCOREEnvelopeBatchSubmissionResponse().getPayload());
		   System.out.println(response.getCOREEnvelopeBatchSubmissionResponse().getErrorMessage());	  
	   }
	 
	return status;
}
protected CONNECTClient<AdapterCOREGenericBatchTransactionPortType> getCONNECTClientUnsecured(
        ServicePortDescriptor<AdapterCOREGenericBatchTransactionPortType> portDescriptor, String url,
        AssertionType assertion) {

    return CONNECTCXFClientFactory.getInstance().getCONNECTClientUnsecured(portDescriptor, url, assertion);
}

public UserType populateUserInfo(X12Submission x12sub) {
	  UserType tempUT = new UserType();
      tempUT.setUserName(x12sub.getProviderNPI());
      HomeCommunityType utOrg = new HomeCommunityType();
      utOrg.setHomeCommunityId(x12sub.getProviderNPI());
      utOrg.setDescription("HCF");
      utOrg.setName("Health care facility");
      tempUT.setOrg(utOrg);
      PersonNameType personName = new PersonNameType();
      personName.setGivenName(x12sub.getProviderNPI());
      personName.setSecondNameOrInitials(x12sub.getProviderNPI());
      personName.setFamilyName(x12sub.getProviderNPI());
      tempUT.setPersonName(personName);
      CeType rolecoded = new CeType();
      rolecoded.setCode(x12sub.getProviderNPI());
      rolecoded.setDisplayName(x12sub.getProviderNPI());
      tempUT.setRoleCoded(rolecoded);
      return tempUT;
      
}

public CeType populatePurpOfDisc() {
	 CeType asPurpDisc = new CeType();
      asPurpDisc.setCode("PAYMENT");
      asPurpDisc.setCodeSystem("2.16.840.1.113883.3.18.7.1");
      asPurpDisc.setCodeSystemName("esMD CMS Purpose");
      asPurpDisc.setCodeSystemVersion("1.0");
      asPurpDisc.setDisplayName("Medical Claim Documentation Review");
      asPurpDisc.setOriginalText("Medical Claim Documentation Review");
     return asPurpDisc;
}

public SamlAuthnStatementType populateSamlAuthnStatementType() {
	  SamlAuthnStatementType samAuth = new SamlAuthnStatementType();
      String dtcurrent = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
      samAuth.setAuthInstant(dtcurrent+'Z');
      Random rand = new Random();
      int nRandom = rand.nextInt(1001);

      samAuth.setSessionIndex(Integer.toString(nRandom));
      samAuth.setAuthContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:X509");
      samAuth.setSubjectLocalityAddress("localhost");
      samAuth.setSubjectLocalityDNSName("cms.hhs.gov");
      
      return samAuth; 
     

}

public SamlAuthzDecisionStatementType populateSamlAuthzDecStmnt(X12Submission x12sub, String uniqueStr) {
   SamlAuthzDecisionStatementType samAuthDec = new SamlAuthzDecisionStatementType();
      samAuthDec.setDecision("permit");
      samAuthDec.setResource(x12sub.getEsmd_Url());
      samAuthDec.setAction(action);
      SamlAuthzDecisionStatementEvidenceType samDecEvid = new SamlAuthzDecisionStatementEvidenceType();
      SamlAuthzDecisionStatementEvidenceAssertionType samDecEvidAss = new SamlAuthzDecisionStatementEvidenceAssertionType();
      SamlAuthzDecisionStatementEvidenceConditionsType samevicond = new SamlAuthzDecisionStatementEvidenceConditionsType();
  	String dtcurrent = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
      samevicond.setNotBefore(dtcurrent+'Z');
      samevicond.setNotOnOrAfter(dtcurrent+'Z');
      samDecEvidAss.setConditions(samevicond);
      samDecEvidAss.setId(uniqueStr);
      samDecEvidAss.setIssueInstant(dtcurrent+'Z');
      samDecEvidAss.setVersion("2.0");
      samDecEvidAss.setIssuerFormat("urn:oasis:names:tc:SAML:1.1:nameidformat:X509SubjectName");
      samDecEvidAss.setIssuer(x12sub.getIssuer());
      List<String> tempContList = samDecEvidAss.getAccessConsentPolicy();
      tempContList.add(action);
      List<String> tempinstcontlist = samDecEvidAss.getInstanceAccessConsentPolicy();
      tempinstcontlist.add(action);
      samDecEvid.setAssertion(samDecEvidAss);
      samAuthDec.setEvidence(samDecEvid);
      return samAuthDec;
     
}
public String sha1(String input) throws NoSuchAlgorithmException {
    MessageDigest mDigest = MessageDigest.getInstance("SHA1");
    byte[] result = mDigest.digest(input.getBytes());
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < result.length; i++) {
        sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
    }
     
    return sb.toString();
}


}
