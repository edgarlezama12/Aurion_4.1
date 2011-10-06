/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.alembic.aurion.admindistribution.nhin;


import org.alembic.aurion.common.nhinccommon.AssertionType;
import org.alembic.aurion.saml.extraction.SamlTokenExtractor;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;
/**
 *
 * @author dunnek
 */
@WebService(serviceName = "RespondingGateway_AdministrativeDistribution", portName = "RespondingGateway_AdministrativeDistribution_PortType", endpointInterface = "org.alembic.aurion.nhinadmindistribution.RespondingGatewayAdministrativeDistributionPortType", targetNamespace = "urn:org:alembic:aurion:nhinadmindistribution", wsdlLocation = "WEB-INF/wsdl/NhinAdministrativeDistribution/NhinAdminDist.wsdl")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@Addressing(enabled=true)
public class NhinAdministrativeDistribution {

    @Resource
    private WebServiceContext context;
    public void sendAlertMessage(oasis.names.tc.emergency.edxl.de._1.EDXLDistribution body) {

        AssertionType assertion = extractAssertion(context);

        getNhinImpl().sendAlertMessage(body, assertion);

    }
    protected AssertionType extractAssertion(WebServiceContext context)
    {
        return SamlTokenExtractor.GetAssertion(context);
    }
    protected NhinAdminDistributionOrchImpl getNhinImpl()
    {
        return new NhinAdminDistributionOrchImpl();
    }

}
