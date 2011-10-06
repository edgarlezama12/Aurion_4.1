/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package org.alembic.aurion.docretrieve.adapter.deferred.request.error.proxy;

import org.alembic.aurion.adapterdocretrievedeferredreqerrorsecured.AdapterDocRetrieveDeferredRequestErrorSecuredPortType;
import org.alembic.aurion.common.nhinccommon.AssertionType;
import org.alembic.aurion.common.nhinccommonadapter.AdapterDocumentRetrieveDeferredRequestErrorSecuredType;
import org.alembic.aurion.connectmgr.ConnectionManagerCache;
import org.alembic.aurion.connectmgr.ConnectionManagerException;
import org.alembic.aurion.nhinclib.NhincConstants;
import org.alembic.aurion.nhinclib.NullChecker;
import org.alembic.aurion.saml.extraction.SamlTokenCreator;
import org.alembic.aurion.webserviceproxy.WebServiceProxyHelper;
import gov.hhs.healthit.nhin.DocRetrieveAcknowledgementType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.util.Map;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

/**
 * Created by
 * User: ralph
 * Date: Jul 26, 2010
 * Time: 2:37:22 PM
 */
public class AdapterDocRetrieveDeferredReqErrorProxyWebServiceSecuredImpl implements AdapterDocRetrieveDeferredReqErrorProxy {
    private Log log = null;
    private static Service cachedService = null;
    private static final String NAMESPACE_URI = "urn:org:alembic:aurion:adapterdocretrievedeferredreqerrorsecured";
    private static final String SERVICE_LOCAL_PART = "AdapterDocRetrieveDeferredRequestErrorSecuredService";
    private static final String PORT_LOCAL_PART = "AdapterDocRetrieveDeferredRequestErrorSecuredPortSoap";
    private static final String WSDL_FILE = "AdapterDocRetrieveDeferredReqErrorSecured.wsdl";
    private static final String WS_ADDRESSING_ACTION = "urn:org:alembic:aurion:adapterdocretrievedeferredreqerrorsecured:CrossGatewayRetrieveRequestErrorSecuredMessage";
    private WebServiceProxyHelper oProxyHelper = null;

    public AdapterDocRetrieveDeferredReqErrorProxyWebServiceSecuredImpl() {
        log = createLogger();
        oProxyHelper = createWebServiceProxyHelper();
    }

    protected Log createLogger() {
        return LogFactory.getLog(getClass());
    }

    protected WebServiceProxyHelper createWebServiceProxyHelper() {
        return new WebServiceProxyHelper();
    }

    /**
     * This method retrieves and initializes the port.
     *
     * @param url The URL for the web service.
     * @return The port object for the web service.
     */
    protected AdapterDocRetrieveDeferredRequestErrorSecuredPortType getPort(String url, String serviceAction, String wsAddressingAction, AssertionType assertion) {
        AdapterDocRetrieveDeferredRequestErrorSecuredPortType port = null;
        Service service = getService();
        if (service != null) {
            log.debug("Obtained service - creating port.");

            port = service.getPort(new QName(NAMESPACE_URI, PORT_LOCAL_PART), AdapterDocRetrieveDeferredRequestErrorSecuredPortType.class);
            oProxyHelper.initializeSecurePort((javax.xml.ws.BindingProvider) port, url, serviceAction, wsAddressingAction, assertion);
        } else {
            log.error("Unable to obtain serivce - no port created.");
        }
        return port;
    }

    /**
     * Retrieve the service class for this web service.
     *
     * @return The service class for this web service.
     */
    protected Service getService() {
        if (cachedService == null) {
            try {
                cachedService = oProxyHelper.createService(WSDL_FILE, NAMESPACE_URI, SERVICE_LOCAL_PART);
            } catch (Throwable t) {
                log.error("Error creating service: " + t.getMessage(), t);
            }
        }
        return cachedService;
    }

    public DocRetrieveAcknowledgementType sendToAdapter(RetrieveDocumentSetRequestType body, AssertionType assertion, String errMsg) {
        log.debug("Begin sendToAdapter");
        DocRetrieveAcknowledgementType response = null;

        try {
            String url = oProxyHelper.getUrlLocalHomeCommunity(NhincConstants.ADAPTER_DOC_RETRIEVE_DEFERRED_REQUEST_ERROR_SECURED_SERVICE_NAME);
            AdapterDocRetrieveDeferredRequestErrorSecuredPortType port = getPort(url, NhincConstants.DOC_RETRIEVE_ACTION, WS_ADDRESSING_ACTION, assertion);

            if (body == null) {
                log.error("Message was null");
            } else if (NullChecker.isNullish(errMsg)) {
                log.error("errMsg was null");
            }else if (port == null) {
                log.error("port was null");
            } else {
                AdapterDocumentRetrieveDeferredRequestErrorSecuredType request = new AdapterDocumentRetrieveDeferredRequestErrorSecuredType();
                request.setRetrieveDocumentSetRequest(body);
                request.setErrorMsg(errMsg);

                response = (DocRetrieveAcknowledgementType) oProxyHelper.invokePort(port, AdapterDocRetrieveDeferredRequestErrorSecuredPortType.class, "crossGatewayRetrieveRequestError", request);
            }
        } catch (Exception ex) {
            log.error("Error calling crossGatewayRetrieveRequestError: " + ex.getMessage(), ex);
            response = new DocRetrieveAcknowledgementType();
            RegistryResponseType regResp = new RegistryResponseType();
            regResp.setStatus(NhincConstants.DOC_RETRIEVE_DEFERRED_REQ_ACK_STATUS_MSG);
            response.setMessage(regResp);
        }

        log.debug("End sendToAdapter");
        return response;
    }
}
