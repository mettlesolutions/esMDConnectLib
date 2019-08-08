package com.mettles.esMDConnectLib;


//import gov.hhs.fha.nhinc.common.nhinccommon.;
//import gov.hhs.fha.nhinc.common.nhinccommon.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryObjectListType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.RegistryPackageType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.InternationalStringType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;
//import oasis.names.tc.ebxml_regrep.xsd.rim._3.*;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import java.io.File;


import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterProvideAndRegisterDocumentSetRequestType;
//import gov.hhs.fha.nhinc.docsubmission.adapter.component.proxy.AdapterComponentXDRPortType;
import gov.hhs.fha.nhinc.adaptercomponentxdrrequest.AdapterComponentXDRRequestPortType;
import gov.hhs.fha.nhinc.docsubmission.adapter.component.deferred.request.proxy.service.AdapterComponentDocSubmissionRequestServicePortDescriptor;
//import gov.hhs.fha.nhinc.docsubmission.adapter.component.proxy.service.AdapterComponentDocSubmissionServicePortDescriptor;
import gov.hhs.fha.nhinc.messaging.client.CONNECTCXFClientFactory;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;
import gov.hhs.healthit.nhin.XDRAcknowledgementType;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExternalIdentifierType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.LocalizedStringType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceAssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceConditionsType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthnStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AssociationType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import org.apache.commons.codec.binary.Base64;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Random;

import javax.xml.bind.JAXBElement;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;

import com.mettles.esMDConnectLib.ReceipientInfo;

public class ConnectSoapClient {
	
	 public static final String DOCUMENT_TYPE = "application/pdf";
	    public static final String DOCUMENT_TYPE_XML = "application/xml";
	    public static final String DOCUMENT_OBJECTTYPE = "urn:uuid:7edca82f-054d47f2-a032-9b2a5b5186c1";
	    public static final String DOC_CLASSIFICATION_ID1="cl15";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME1="urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION1="2.16.840.1.113883.13.34.110.1.1000.1";
	    public static final String DOC_FORMAT_CODE_TYPE="Scanned PDF Document in Clinical Document Architecture (CDA) C62 Construct";
	    public static final String DOC_CLASSIFICATION_ID2="cl01";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME2="urn:uuid:93606bcf-9494-43ec-9b4ea7748d1a838d";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION2="author";
	    public static final String DOC_CLASSIFICATION_ID3="cl02";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME3="urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION3="2.16.840.1.113883.13.34.110.1.1000.1";
	    public static final String DOC_CLASS_CODE_TYPE="Unstructured Document Submission";
	    public static final String DOC_CLASSIFICATION_ID4="cl03";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME4="urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION4="2.16.840.1.113883.5.25";
	    public static final String DOC_CONFIDENTIALITY_CODE_TYPE="VeryRestricted";
	    public static final String DOC_CLASSIFICATION_ID5="cl06";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME5="urn:uuid:cccf5598-8b07-4b77-a05eae952c785ead";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION5="2.16.840.1.113883.13.34.110.1.1000.1";
	    public static final String DOC_PRACSETTINGS_CODE_TYPE="NA";
	    public static final String DOC_CLASSIFICATION_ID6="cl05";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME6="urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION6="2.16.840.1.113883.13.34.110.1.1000.1";
	    public static final String DOC_HEALTHCAREFACILITY_CODE_TYPE="Health Information Handler (HIH)";
	    public static final String DOC_CLASSIFICATION_ID7="cl07";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME7="urn:uuid:f0306f51-975f-434e-a61cc59651d33983";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION7="2.16.840.1.113883.13.34.110.1.1000.1";
	    public static final String DOC_CODINGSCHEME_CODE_TYPE="Outpatient Evaluation And Management";
	    public static final String DOC_CLASSIFICATION_ID8="cl08";
	    public static final String DOC_CLASSIFICATION_ID_SCHEME8="urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a";
	    public static final String DOC_CLASSIFICATION_ID_NODE_REPRESENTATION8="2.16.840.1.113883.13.34.110.1.1000.1";
	    public static final String DOC_CLASSCODE_TYPE="Unstructured Document Submission";
	    public static final String DOC_HASH_STRING = "ad18814418693512b767676006a21d8ec7291e84";
	    public static final String DOC_EXTROBJ_NAME = "Claim Supporting Medical Documentation";
	    
	    
	    public SubmissionStatus SoapClientCall(DocSubmissionData sub, String uniquestr, String parentID) throws PropertyException{
	        String url = "http://val.mettles.com:8080/Adapter/esmd/AdapterService/AdapterDocSubmissionDeferredRequest";
	        int retVal = -1;
	        SubmissionStatus substatus = new SubmissionStatus();
	       
	        ProvideAndRegisterDocumentSetRequestType msg = new ProvideAndRegisterDocumentSetRequestType();

	        AdapterProvideAndRegisterDocumentSetRequestType type = new AdapterProvideAndRegisterDocumentSetRequestType();

	        AdapterProvideAndRegisterDocumentSetRequestType request = new AdapterProvideAndRegisterDocumentSetRequestType();
	        //-------------------------///
	        
	      //  String uniqueid = null;


	        SubmitObjectsRequest sor = new SubmitObjectsRequest();
	        sor.setId("999");
	        sor.setComment("esMD Claim Document Submission in response to Review Contractor ADR Letter");

	        RegistryObjectListType rol = new RegistryObjectListType();

	          System.out.println("Entered connect call");
	        ExtrinsicObjectType eot = new ExtrinsicObjectType();
	        RegistryPackageType rpt = new RegistryPackageType();
	        rpt.setId("SubmissionSet01");


	        rpt.getSlot().add(createSlotIndividual("esMDClaimId",sub.getEsMDClaimID()));
	        rpt.getSlot().add(createSlotIndividual("esMDCaseId",sub.getEsmdCaseId()));
	        rpt.getSlot().add(createSlotIndividual("intendedRecipient",sub.getIntendedRecepient().getOid()));
	        if(!sub.getSplitStr().equals("1-1")){
	            rpt.getSlot().add(createSlotIndividual("parentUniqueNumber","_"+parentID));
	            rpt.getSlot().add(createSlotIndividual("splitNumber",sub.getSplitStr()));
	        }
	        System.out.println("Entered connect call1");
	        ClassificationType tempcls = new ClassificationType();
	        tempcls.setId("888");
	        tempcls.setClassifiedObject("SubmissionSet01");
	        tempcls.setClassificationNode("urn:uuid:"+UUID.randomUUID().toString());
	        JAXBElement<ClassificationType> jaxbElement0 =
	                new JAXBElement( new QName("Classification"), ClassificationType.class, null);
	        jaxbElement0.setValue(tempcls);
	        rol.getIdentifiable().add(jaxbElement0);
	        JAXBElement<RegistryPackageType> jaxbElement =
	                new JAXBElement( new QName("RegistryPackage"), RegistryPackageType.class, null);
	        jaxbElement.setValue(rpt);
	        rol.getIdentifiable().add(jaxbElement);



	        //rpt.getClassification().add(tempcls);

	        ExternalIdentifierType tempextId = new ExternalIdentifierType();
	        tempextId.setId("ei03");
	        tempextId.setIdentificationScheme("urn:uuid:"+UUID.randomUUID().toString());

	        tempextId.setValue("2.16.840.1.113883.13.34.110.1.1000.1^^^&"+sub.getIntendedRecepient().getOid());
	        tempextId.setRegistryObject("SubmissionSet01");
	        InternationalStringType tempIntLoc = new InternationalStringType();
	        LocalizedStringType temploc = new LocalizedStringType();
	        temploc.setValue("XDSDocumentEntry.patientId");
	        tempIntLoc.getLocalizedString().add(temploc) ;
	        tempextId.setName(tempIntLoc);
	        rpt.getExternalIdentifier().add(tempextId);
	        System.out.println("Entered connect call2");
	        ExternalIdentifierType tempextId1 = new ExternalIdentifierType();
	        tempextId1.setId("ei04");
	        tempextId1.setIdentificationScheme("urn:uuid:"+UUID.randomUUID().toString());
	        tempextId1.setValue("12.16.840.1.113883.13.34.110.2");
	        tempextId1.setRegistryObject("SubmissionSet01");
	        InternationalStringType tempIntLoc1 = new InternationalStringType();
	        LocalizedStringType temploc1 = new LocalizedStringType();
	        temploc1.setValue("XDSSubmissionSet.sourceId");
	        tempIntLoc1.getLocalizedString().add(temploc1) ;
	        tempextId1.setName(tempIntLoc1);
	        rpt.getExternalIdentifier().add(tempextId1);

	        ExternalIdentifierType tempextId2 = new ExternalIdentifierType();
	        tempextId2.setId("ei05");
	        tempextId2.setIdentificationScheme("urn:uuid:"+uniquestr);
	        tempextId2.setValue(uniquestr);
	        tempextId2.setRegistryObject("SubmissionSet01");
	        InternationalStringType tempIntLoc2 = new InternationalStringType();
	        LocalizedStringType temploc2 = new LocalizedStringType();
	        temploc2.setValue("XDSSubmissionSet.uniqueId");
	        tempIntLoc2.getLocalizedString().add(temploc2) ;
	        tempextId2.setName(tempIntLoc2);
	        rpt.getExternalIdentifier().add(tempextId2);


	        InternationalStringType tempIntLocjaxb = new InternationalStringType();
	        LocalizedStringType templocjaxb = new LocalizedStringType();
	        System.out.println("Entered connect call3");
	        templocjaxb.setValue(sub.getTitle());
	        tempIntLocjaxb.getLocalizedString().add(templocjaxb) ;
	       rpt.setName(tempIntLocjaxb);

	        ClassificationType temp = createClassificationType("cl11","urn:uuid:"+UUID.randomUUID().toString(),"author","SubmissionSet01");
	        String authinst = "Institution";
	        if(sub.getAuthorType() != null)
	            authinst = sub.getAuthorType();

	        if(authinst.equals("Institution"))
	            temp.getSlot().add(createSlotIndividual("authorInstitution",sub.getAuthorNPI()));
	        else
	            temp.getSlot().add(createSlotIndividual("authorPerson",sub.getAuthorNPI()));

	        rpt.getClassification().add(temp);

	        ClassificationType temp1 = createClassificationType("cl09","urn:uuid:aa543740-bdda-424e-8c96-df4873be8500","2.16.840.1.113883.13.34.110.1.1000.1","SubmissionSet01");
	        temp1.getSlot().add(createSlotIndividual("contentTypeCode",sub.getPurposeOfSubmission().getContentType()));
	        temp1.setName(createInternationalStringType(sub.getPurposeOfSubmission().getPurposeOfSubmission()));

	        rpt.getClassification().add(temp1);
	        System.out.println("Calling Document Append");
	        List<ProvideAndRegisterDocumentSetRequestType.Document> tempDtlist = msg.getDocument();
	        //List<Document> doc_set = sub.getDocument();
	        Iterator<DocumentPayload> documentIterator = sub.getDocArryList().iterator();
	        ArrayList<String> filestoDel = new ArrayList<>();
	        DocumentPrepare dpTempObj = new DocumentPrepare();
	        int idx = 0;

	        while(documentIterator.hasNext()){
	        	DocumentPayload tempdt  = documentIterator.next();

	            JAXBElement<ExtrinsicObjectType> jaxbElement2 =
	                    new JAXBElement( new QName("ExtrinsicObject"), ExtrinsicObjectType.class, null);
	            jaxbElement2.setValue(CreateExtrinicObject( sub,  tempdt,idx));

	            rol.getIdentifiable().add(jaxbElement2);

	            ProvideAndRegisterDocumentSetRequestType.Document dt = new ProvideAndRegisterDocumentSetRequestType.Document();
	            String docid = "Document",document_id;
	            String associd = "as",association_id;
	            if(idx<10) {
	                document_id = docid + "0" + Integer.toString(idx);
	                association_id = associd + "0" + Integer.toString(idx);
	            }
	            else {
	                document_id = docid + Integer.toString(idx);
	                association_id =  associd + Integer.toString(idx);
	            }


	            dt.setId(document_id);
	            
	            String tempFilepath="";
	            System.out.println("Received base64 payload leng"+tempdt.getBase64Payload().length());
	            tempFilepath = dpTempObj.prepareFile(tempdt.getBase64Payload(), tempdt.getFiletype());
          
	         
	            filestoDel.add(tempFilepath);
	           DataSource fds = new FileDataSource(tempFilepath);
	          //  String base64str = Base64.encodeBase64String(tempFilepath.getBytes());
	        //    System.out.println(""+base64str);
	          //  DataSource fds = new ByteArrayDataSource(tempFilepath.getBytes(),"application/xml");

	            DataHandler handler = new DataHandler(fds);
	            dt.setValue(handler);

	            tempDtlist.add(dt);
	            idx++;
	            JAXBElement<AssociationType1> jaxbElement3 =
	                    new JAXBElement( new QName("Association"), AssociationType1.class, null);
	            AssociationType1 assocTemp = new AssociationType1();
	            jaxbElement3.setValue(assocTemp);
	            assocTemp.setId(association_id);
	            assocTemp.setAssociationType("HasMember");
	            assocTemp.setSourceObject("SubmissionSet01");
	            assocTemp.setTargetObject(document_id);
	            assocTemp.getSlot().add(createSlotIndividual("SubmissionSetStatus","Original"));

	            rol.getIdentifiable().add(jaxbElement3);
	        }

	        sor.setRegistryObjectList(rol);
	        msg.setSubmitObjectsRequest(sor);
	        AssertionType assertion = new AssertionType();
	        HomeCommunityType hc = new HomeCommunityType();
	        hc.setName(sub.getHihconfigvals().getHIH_Name());
	        hc.setDescription(sub.getHihconfigvals().getHIH_Description());
	        hc.setHomeCommunityId(sub.getHihconfigvals().getHIH_Oid());
	        assertion.setHomeCommunity(hc);
	        List<String> temppatiendidList = assertion.getUniquePatientId();
	        temppatiendidList.add("urn:oid:"+sub.getIntendedRecepient().getOid());
	        UserType tempUT = new UserType();
	        tempUT.setUserName(sub.getAuthorNPI());
	        HomeCommunityType utOrg = new HomeCommunityType();
	        utOrg.setHomeCommunityId(sub.getAuthorNPI());
	        utOrg.setDescription("HCF");
	        utOrg.setName("Health care facility");
	        tempUT.setOrg(utOrg);
	        PersonNameType personName = new PersonNameType();
	        personName.setGivenName(sub.getAuthorNPI());
	        personName.setSecondNameOrInitials(sub.getAuthorNPI());
	        personName.setFamilyName(sub.getAuthorNPI());
	        tempUT.setPersonName(personName);
	        CeType rolecoded = new CeType();
	        rolecoded.setCode(sub.getAuthorNPI());
	        rolecoded.setDisplayName(sub.getAuthorNPI());
	        tempUT.setRoleCoded(rolecoded);
	        assertion.setUserInfo(tempUT);

	        assertion.setNationalProviderId(sub.getAuthorNPI());
	        CeType asPurpDisc = new CeType();
	        asPurpDisc.setCode("PAYMENT");
	        asPurpDisc.setCodeSystem("2.16.840.1.113883.3.18.7.1");
	        asPurpDisc.setCodeSystemName("esMD CMS Purpose");
	        asPurpDisc.setCodeSystemVersion("1.0");
	        asPurpDisc.setDisplayName("Medical Claim Documentation Review");
	        asPurpDisc.setOriginalText("Medical Claim Documentation Review");
	        assertion.setPurposeOfDisclosureCoded(asPurpDisc);
	        SamlAuthnStatementType samAuth = new SamlAuthnStatementType();
	        String dtcurrent = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
	        samAuth.setAuthInstant(dtcurrent+'Z');
	        Random rand = new Random();
	        int nRandom = rand.nextInt(1001);

	        samAuth.setSessionIndex(Integer.toString(nRandom));
	        samAuth.setAuthContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:X509");
	        samAuth.setSubjectLocalityAddress("localhost");
	        samAuth.setSubjectLocalityDNSName("cms.hhs.gov");
	        assertion.setSamlAuthnStatement(samAuth);

	        SamlAuthzDecisionStatementType samAuthDec = new SamlAuthzDecisionStatementType();
	        samAuthDec.setDecision("permit");
	        samAuthDec.setResource(sub.getHihconfigvals().getHIH_Esmd_Url());
	        samAuthDec.setAction(sub.getPurposeOfSubmission().getPurposeOfSubmission());
	        SamlAuthzDecisionStatementEvidenceType samDecEvid = new SamlAuthzDecisionStatementEvidenceType();
	        SamlAuthzDecisionStatementEvidenceAssertionType samDecEvidAss = new SamlAuthzDecisionStatementEvidenceAssertionType();
	        SamlAuthzDecisionStatementEvidenceConditionsType samevicond = new SamlAuthzDecisionStatementEvidenceConditionsType();
	        samevicond.setNotBefore(dtcurrent+'Z');
	        samevicond.setNotOnOrAfter(dtcurrent+'Z');
	        samDecEvidAss.setConditions(samevicond);
	        samDecEvidAss.setId(uniquestr);
	        samDecEvidAss.setIssueInstant(dtcurrent+'Z');
	        samDecEvidAss.setVersion("2.0");
	        samDecEvidAss.setIssuerFormat("urn:oasis:names:tc:SAML:1.1:nameidformat:X509SubjectName");
	        if(sub.getHihconfigvals().getIssuer() != null)
	        	 samDecEvidAss.setIssuer(sub.getHihconfigvals().getIssuer());
	        else	
	        samDecEvidAss.setIssuer("cn=val.mettles.com, o=\"Mettle Solutions, LLC.\", l=Columbia, st=Maryland, c=US");
	        List<String> tempContList = samDecEvidAss.getAccessConsentPolicy();
	        tempContList.add(sub.getPurposeOfSubmission().getPurposeOfSubmission());
	        List<String> tempinstcontlist = samDecEvidAss.getInstanceAccessConsentPolicy();
	        tempinstcontlist.add(sub.getPurposeOfSubmission().getPurposeOfSubmission());
	        samDecEvid.setAssertion(samDecEvidAss);
	        samAuthDec.setEvidence(samDecEvid);
	        assertion.setSamlAuthzDecisionStatement(samAuthDec);
	        assertion.setMessageId("urn:oid:"+UUID.randomUUID().toString());

	        //-------------------------//
	        request.setProvideAndRegisterDocumentSetRequest(msg);

	        request.setAssertion(assertion);

	        ServicePortDescriptor<AdapterComponentXDRRequestPortType> portDescriptor = new AdapterComponentDocSubmissionRequestServicePortDescriptor();

	        CONNECTClient<AdapterComponentXDRRequestPortType> client = getCONNECTClientUnsecured(portDescriptor, url,assertion);
	      //  client.enableMtom();
	        try {
	            XDRAcknowledgementType response = (XDRAcknowledgementType) client.invokePort(AdapterComponentXDRRequestPortType.class,"provideAndRegisterDocumentSetBRequest", request);
	            if(response == null){
	                System.out.println("Response Object is null");
	                substatus.setMessage("Response Object is Null");
	                substatus.setStatus(1);
	                return substatus;
	            }
	            System.out.println(response.getMessage().getStatus());
	           // String str = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:RequestAccepted";
	            StatusInfo statChng = new StatusInfo();
	            if(response.getMessage().getStatus().equals("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:RequestAccepted")){
	              retVal = 0;
	              System.out.println("Adding StatChange Item");
	              
	                statChng.setResult("Success");
	                statChng.setStatus(response.getMessage().getStatus());
	                // datamanager.commit(statChng);
	                substatus.setMessage("Success");
	                substatus.setStatus(0);

	            }else {
	               
	                statChng.setResult("Failure");
	                statChng.setStatus(response.getMessage().getStatus());
	                substatus.setMessage("Failure");
	                substatus.setStatus(2);
	                

	                    RegistryResponseType rr =response.getMessage();
	                    ArrayList<ErrorInfo> temperrList = new ArrayList<>();
	                  
	                     List<RegistryError>  errList = rr.getRegistryErrorList().getRegistryError();

	            for (Iterator iterator = errList.iterator(); iterator.hasNext();) {
	                RegistryError value = (RegistryError) iterator.next();
	                ErrorInfo tempErr = new ErrorInfo();
	                tempErr.setErrorCode(value.getErrorCode());
	                tempErr.setValue(value.getValue());
	                tempErr.setCodeContext(value.getCodeContext());
	                tempErr.setSeverity(value.getSeverity());
	                temperrList.add(tempErr);


	            }
	            substatus.setError(temperrList);
	            }
	            substatus.setStatchng(statChng);







	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }finally {
	            Iterator it = filestoDel.iterator();
	            while(it.hasNext()) {
	                new File(it.next().toString()).delete();
	            }
	        }

	      return substatus;
	    }

	    protected SlotType1 createSlotIndividual(String name, String value) {
	        ArrayList<String> values = new ArrayList<>();
	        values.add(value);
	        return createSlot(name,values);
	    }

	    protected SlotType1 createSlot(String name, ArrayList<String> values) {
	        SlotType1 slot = new SlotType1();
	        slot.setName(name);
	        ValueListType vls = new ValueListType();
	        for (Iterator iterator = values.iterator(); iterator.hasNext();) {
	            String value = (String) iterator.next();
	            vls.getValue().add(value);

	        }
	        slot.setValueList(vls);
	        return slot;
	    }
	    protected InternationalStringType createInternationalStringType(String value){
	        InternationalStringType  locVar = new InternationalStringType();
	        LocalizedStringType tempLocStr = new LocalizedStringType();
	        tempLocStr.setValue(value);
	        locVar.getLocalizedString().add(tempLocStr);
	        return locVar;
	    }
	    protected ExtrinsicObjectType  CreateExtrinicObject(DocSubmissionData sub, DocumentPayload dt,int idx){
	        ExtrinsicObjectType tempExtObj = new ExtrinsicObjectType();
	        String docid = "Document";
	        if(idx<10)
	            docid = docid+"0"+Integer.toString(idx);
	        else
	            docid = docid + Integer.toString(idx);

	        tempExtObj.setId(docid);
	        tempExtObj.setMimeType(dt.getFiletype());
	      

	        tempExtObj.setObjectType(DOCUMENT_OBJECTTYPE);


	        ClassificationType tempextClass = createClassificationType(DOC_CLASSIFICATION_ID1,DOC_CLASSIFICATION_ID_SCHEME1,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION1,docid);
	        tempextClass.getSlot().add(createSlotIndividual("formatCode","1"));
	        tempextClass.setName(createInternationalStringType(DOC_FORMAT_CODE_TYPE));
	        tempExtObj.getClassification().add(tempextClass);
	        ClassificationType tempextClass1 = createClassificationType(DOC_CLASSIFICATION_ID2,DOC_CLASSIFICATION_ID_SCHEME2,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION2,docid);
	        tempextClass1.getSlot().add(createSlotIndividual("authorInstitution",sub.getAuthorNPI()));
	        tempExtObj.getClassification().add(tempextClass1);
	        ClassificationType tempextClass2 = createClassificationType(DOC_CLASSIFICATION_ID3,DOC_CLASSIFICATION_ID_SCHEME3,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION3,docid);
	        tempextClass2.getSlot().add(createSlotIndividual("classCode","1"));
	        tempextClass2.setName(createInternationalStringType(DOC_CLASS_CODE_TYPE));
	        tempExtObj.getClassification().add(tempextClass2);
	        ClassificationType tempextClass3 = createClassificationType(DOC_CLASSIFICATION_ID4,DOC_CLASSIFICATION_ID_SCHEME4,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION4,docid);
	        tempextClass3.getSlot().add(createSlotIndividual("confidentialityCode","V"));
	        tempextClass3.setName(createInternationalStringType(DOC_CONFIDENTIALITY_CODE_TYPE));
	        tempExtObj.getClassification().add(tempextClass3);
	        ClassificationType tempextClass4 = createClassificationType(DOC_CLASSIFICATION_ID5,DOC_CLASSIFICATION_ID_SCHEME5,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION5,docid);
	        tempextClass4.getSlot().add(createSlotIndividual("practiceSettingCode","1"));
	        tempextClass4.setName(createInternationalStringType(DOC_PRACSETTINGS_CODE_TYPE));
	        tempExtObj.getClassification().add(tempextClass4);
	        ClassificationType tempextClass5 = createClassificationType(DOC_CLASSIFICATION_ID6,DOC_CLASSIFICATION_ID_SCHEME6,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION6,docid);
	        tempextClass5.getSlot().add(createSlotIndividual("healthcareFacilityTypeCode","1"));
	        tempextClass5.setName(createInternationalStringType(DOC_HEALTHCAREFACILITY_CODE_TYPE));
	        tempExtObj.getClassification().add(tempextClass5);
	        ClassificationType tempextClass6 = createClassificationType(DOC_CLASSIFICATION_ID7,DOC_CLASSIFICATION_ID_SCHEME7,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION7,docid);
	        tempextClass6.getSlot().add(createSlotIndividual("codingScheme","2"));
	        tempextClass6.setName(createInternationalStringType(DOC_CODINGSCHEME_CODE_TYPE));
	        tempExtObj.getClassification().add(tempextClass6);
	        ClassificationType tempextClass7 = createClassificationType(DOC_CLASSIFICATION_ID8,DOC_CLASSIFICATION_ID_SCHEME8,DOC_CLASSIFICATION_ID_NODE_REPRESENTATION8,docid);
	        tempextClass7.getSlot().add(createSlotIndividual("classCode","1"));
	        tempextClass7.setName(createInternationalStringType(DOC_CLASSCODE_TYPE));
	        tempExtObj.getClassification().add(tempextClass7);
	        String dtcurrent = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss").format(Calendar.getInstance().getTime());

	        tempExtObj.getSlot().add(createSlotIndividual("creationTime",dtcurrent));
	        tempExtObj.getSlot().add(createSlotIndividual("hash",DOC_HASH_STRING));
	        String langcode = "en-us";
	        if(dt.getLanguage() != null)
	            langcode = dt.getLanguage();

	        tempExtObj.getSlot().add(createSlotIndividual("languageCode",langcode));
	        tempExtObj.getSlot().add(createSlotIndividual("legalAuthenticator","NA"));
	        SlotType1 slottemp = new SlotType1();
	        slottemp.setName("attachmentControlNumber");
	        ValueListType tempsltype = new ValueListType();
	        tempsltype.getValue();
	        if((dt.getAttachmentControlNumber() != null)|| (dt.getAttachmentControlNumber() != "")){
	            tempsltype.getValue().add(dt.getAttachmentControlNumber());
	        }

	        slottemp.setValueList(tempsltype);
	        //slottemp.getValueList();
	        tempExtObj.getSlot().add(slottemp);


	        tempExtObj.getSlot().add(createSlotIndividual("serviceStartTime",dtcurrent));
	        tempExtObj.getSlot().add(createSlotIndividual("serviceEndTime",dtcurrent));
	        tempExtObj.getSlot().add(createSlotIndividual("size",dt.getFilesize()));
	        tempExtObj.setName(createInternationalStringType(DOC_EXTROBJ_NAME));

	        tempExtObj.setDescription(createInternationalStringType(sub.getPurposeOfSubmission().getPurposeOfSubmission()));
	        return tempExtObj;
	    }
	   /* protected void ReplaceDocumentContentXML(String base64Str){
	        try {
	            Path path = Paths.get("C:/New folder/record.xml");
	            Stream<String> lines = Files.lines(path);
	            List <String> replaced = lines.map(line -> line.replaceAll("foo", "bar")).collect(Collectors.toList());
	            Files.write(path, replaced);
	            lines.close();
	            System.out.println("Find and Replace done!!!");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }*/
	    protected ExternalIdentifierType createExternalIdentifier(String id, String value) {
	        ExternalIdentifierType e = new ExternalIdentifierType();
	        e.setId(id);
	        LocalizedStringType l = new LocalizedStringType();
	        l.setValue(value);
	        e.getName().getLocalizedString().add(l);
	        return e;
	    }
	    protected ClassificationType createClassificationType(String id, String classificationScheme,String nodeRepresentation,String classifiedObject) {
	        ClassificationType e = new ClassificationType();
	        e.setId(id);
	        e.setClassificationScheme(classificationScheme);
	        e.setNodeRepresentation(nodeRepresentation);
	        e.setClassifiedObject(classifiedObject);
	        return e;
	    }
	    protected CONNECTClient<AdapterComponentXDRRequestPortType> getCONNECTClientUnsecured(
	            ServicePortDescriptor<AdapterComponentXDRRequestPortType> portDescriptor, String url,
	            AssertionType assertion) {

	        return CONNECTCXFClientFactory.getInstance().getCONNECTClientUnsecured(portDescriptor, url, assertion);
	    }

}
