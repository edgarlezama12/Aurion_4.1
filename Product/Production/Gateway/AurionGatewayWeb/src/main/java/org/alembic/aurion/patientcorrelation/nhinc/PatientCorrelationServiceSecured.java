/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package org.alembic.aurion.patientcorrelation.nhinc;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
/**
 *
 * @author Sai Valluripalli
 */
@WebService(serviceName = "PatientCorrelationServiceSecured", portName = "PatientCorrelationSecuredPort", endpointInterface = "org.alembic.aurion.nhinccomponentpatientcorrelation.PatientCorrelationSecuredPortType", targetNamespace = "urn:org:alembic:aurion:nhinccomponentpatientcorrelation", wsdlLocation = "WEB-INF/wsdl/PatientCorrelationServiceSecured/NhincComponentPatientCorrelationSecured.wsdl")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@HandlerChain(file = "PatientCorrelationServiceSoapHeaderHandler.xml")
public class PatientCorrelationServiceSecured {

    @Resource
    private WebServiceContext context;

    public org.hl7.v3.RetrievePatientCorrelationsSecuredResponseType retrievePatientCorrelations(org.hl7.v3.RetrievePatientCorrelationsSecuredRequestType retrievePatientCorrelationsRequest) {
        return new PatientCorrelationServiceSecuredImpl().retrievePatientCorrelations(retrievePatientCorrelationsRequest, context);
    }

    public org.hl7.v3.AddPatientCorrelationSecuredResponseType addPatientCorrelation(org.hl7.v3.AddPatientCorrelationSecuredRequestType addPatientCorrelationRequest) {
        return new PatientCorrelationServiceSecuredImpl().addPatientCorrelation(addPatientCorrelationRequest, context);
    }
}
