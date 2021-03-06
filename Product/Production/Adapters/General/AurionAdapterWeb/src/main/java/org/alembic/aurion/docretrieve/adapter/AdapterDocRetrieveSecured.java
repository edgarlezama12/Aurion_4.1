/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package org.alembic.aurion.docretrieve.adapter;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;

/**
 *
 * @author Sai Valluripalli
 */
@WebService(serviceName = "AdapterDocRetrieveSecured", portName = "AdapterDocRetrieveSecuredPortSoap", endpointInterface = "org.alembic.aurion.adapterdocretrievesecured.AdapterDocRetrieveSecuredPortType", targetNamespace = "urn:org:alembic:aurion:adapterdocretrievesecured", wsdlLocation = "WEB-INF/wsdl/AdapterDocRetrieveSecured/AdapterDocRetrieveSecured.wsdl")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@HandlerChain(file = "AdapterDocRetrieveSoapHandler.xml")
public class AdapterDocRetrieveSecured {

    @Resource
    private WebServiceContext context;

    public ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType respondingGatewayCrossGatewayRetrieve(ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType body) {
        return new AdapterDocRetrieveImpl().respondingGatewayCrossGatewayRetrieveSecured(body, context);
    }

}
