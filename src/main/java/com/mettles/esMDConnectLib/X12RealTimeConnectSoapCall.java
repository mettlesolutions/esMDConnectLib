package com.mettles.esMDConnectLib;

import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterCOREEnvelopeRealTimeRequestType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterCOREEnvelopeRealTimeResponseType;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeRealTimeRequest;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.adaptercomponentxdrrequest.AdapterComponentXDRRequestPortType;
import gov.hhs.fha.nhinc.adaptercore.AdapterCORETransactionPortType;
import gov.hhs.fha.nhinc.corex12.ds.realtime.adapter.proxy.service.AdapterX12RealTimeUnsecuredServicePortDescriptor;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import gov.hhs.fha.nhinc.messaging.client.CONNECTCXFClientFactory;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;
import gov.hhs.healthit.nhin.XDRAcknowledgementType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthnStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceAssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceConditionsType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;

public class X12RealTimeConnectSoapCall {
	
	public static String CORERuleVersion = "v2.2.0";
	public static String ProcessingMode = "RealTime";
	public static String PayloadType = "X12_278_Request_005010X217E1_2";
	public static String action = "Response to Additional Documentation Request (ADR)";
	
	public X12SubmissionStatus RealTimeConnectSoapClient(X12Submission x12sub, String uniqueStr, String ediPayload,String connectServerUrl) {
		int status = 0;
		String url = null;
		X12SubmissionStatus retVal = new X12SubmissionStatus();
		
		if(connectServerUrl == null)
			url = "http://localhost:8080/Adapter/CORE_X12DocumentSubmission/A_0/CORETransaction/AdapterCORETransactionUnsecured";
		else
			url = connectServerUrl + "/Adapter/CORE_X12DocumentSubmission/A_0/CORETransaction/AdapterCORETransactionUnsecured";
		
		AdapterCOREEnvelopeRealTimeRequestType realtimerequest = new AdapterCOREEnvelopeRealTimeRequestType();
		AdapterCOREEnvelopeRealTimeResponseType response = null;
		COREEnvelopeRealTimeRequest coreenvreq = new COREEnvelopeRealTimeRequest();
		coreenvreq.setCORERuleVersion(CORERuleVersion);
		coreenvreq.setPayloadType(PayloadType);
		coreenvreq.setProcessingMode(ProcessingMode);
		String dtcurrent = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
		coreenvreq.setTimeStamp(dtcurrent+'Z');
		coreenvreq.setPayloadID(uniqueStr);
		coreenvreq.setSenderID("urn:oid:"+x12sub.gethIHOID());
		coreenvreq.setReceiverID("urn:oid:"+x12sub.getRcOid());
		coreenvreq.setPayload(ediPayload);
		realtimerequest.setCOREEnvelopeRealTimeRequest(coreenvreq);
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
	       
	        asser.setSamlAuthzDecisionStatement( populateSamlAuthzDecStmnt(x12sub, uniqueStr));
	       
	        asser.setMessageId("urn:oid:"+UUID.randomUUID().toString());
		realtimerequest.setAssertion(asser);
		
		ServicePortDescriptor<AdapterCORETransactionPortType> coreport = new AdapterX12RealTimeUnsecuredServicePortDescriptor();
		
		 CONNECTClient<AdapterCORETransactionPortType> client = getCONNECTClientUnsecured(coreport,url,asser);
		   try {
			   response = (AdapterCOREEnvelopeRealTimeResponseType) client.invokePort(AdapterCORETransactionPortType.class,"realTimeTransaction", realtimerequest);
		   }catch(Exception e) {
			   
		   }
		   if(response != null) {
			  
			   retVal.setResponsePayload(response.getCOREEnvelopeRealTimeResponse().getPayload());
			   retVal.setError_code(response.getCOREEnvelopeRealTimeResponse().getErrorCode());
			   retVal.setError_message(response.getCOREEnvelopeRealTimeResponse().getErrorMessage());
			   retVal.setPayloadType(response.getCOREEnvelopeRealTimeResponse().getPayloadType());
			   retVal.setPayloadID(response.getCOREEnvelopeRealTimeResponse().getPayloadID());
			
		   }else {
			   retVal.setError_code("-1");
			   retVal.setError_message("Unable to send Message");
		   }
		 
		
		return retVal;
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
	   protected CONNECTClient<AdapterCORETransactionPortType> getCONNECTClientUnsecured(
	            ServicePortDescriptor<AdapterCORETransactionPortType> portDescriptor, String url,
	            AssertionType assertion) {

	        return CONNECTCXFClientFactory.getInstance().getCONNECTClientUnsecured(portDescriptor, url, assertion);
	    }
	   
	
}
