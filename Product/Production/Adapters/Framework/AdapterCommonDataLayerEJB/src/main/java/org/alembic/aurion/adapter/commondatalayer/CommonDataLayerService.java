/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package org.alembic.aurion.adapter.commondatalayer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3.1-hudson-749-SNAPSHOT
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "CommonDataLayerService", targetNamespace = "urn:org:alembic:aurion:adapter:commondatalayer", wsdlLocation = "file:/D:/GlassFishESBV2.1/NetBeansProjects6.5.1/NHINC/Current/Product/Production/Adapters/Framework/AdapterCommonDataLayerEJB/src/conf/xml-resources/web-services/AdapterCommonDataLayer/wsdl/AdapterCommonDataLayer.wsdl")
public class CommonDataLayerService
    extends Service
{

    private final static URL COMMONDATALAYERSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(org.alembic.aurion.adapter.commondatalayer.CommonDataLayerService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = org.alembic.aurion.adapter.commondatalayer.CommonDataLayerService.class.getResource(".");
            url = new URL(baseUrl, "file:/D:/GlassFishESBV2.1/NetBeansProjects6.5.1/NHINC/Current/Product/Production/Adapters/Framework/AdapterCommonDataLayerEJB/src/conf/xml-resources/web-services/AdapterCommonDataLayer/wsdl/AdapterCommonDataLayer.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/D:/GlassFishESBV2.1/NetBeansProjects6.5.1/NHINC/Current/Product/Production/Adapters/Framework/AdapterCommonDataLayerEJB/src/conf/xml-resources/web-services/AdapterCommonDataLayer/wsdl/AdapterCommonDataLayer.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        COMMONDATALAYERSERVICE_WSDL_LOCATION = url;
    }

    public CommonDataLayerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CommonDataLayerService() {
        super(COMMONDATALAYERSERVICE_WSDL_LOCATION, new QName("urn:org:alembic:aurion:adapter:commondatalayer", "CommonDataLayerService"));
    }

    /**
     * 
     * @return
     *     returns CommonDataLayerPortType
     */
    @WebEndpoint(name = "CommonDataLayerPort")
    public CommonDataLayerPortType getCommonDataLayerPort() {
        return super.getPort(new QName("urn:org:alembic:aurion:adapter:commondatalayer", "CommonDataLayerPort"), CommonDataLayerPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CommonDataLayerPortType
     */
    @WebEndpoint(name = "CommonDataLayerPort")
    public CommonDataLayerPortType getCommonDataLayerPort(WebServiceFeature... features) {
        return super.getPort(new QName("urn:org:alembic:aurion:adapter:commondatalayer", "CommonDataLayerPort"), CommonDataLayerPortType.class, features);
    }

}
