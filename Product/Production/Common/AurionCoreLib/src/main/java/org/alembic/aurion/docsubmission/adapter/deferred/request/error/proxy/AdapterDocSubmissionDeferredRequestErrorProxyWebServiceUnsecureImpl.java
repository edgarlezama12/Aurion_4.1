/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package org.alembic.aurion.docsubmission.adapter.deferred.request.error.proxy;

import org.alembic.aurion.adapterxdrrequesterror.AdapterXDRRequestErrorPortType;
import org.alembic.aurion.common.nhinccommon.AssertionType;
import org.alembic.aurion.common.nhinccommonadapter.AdapterProvideAndRegisterDocumentSetRequestErrorType;
import org.alembic.aurion.nhinclib.NhincConstants;
import org.alembic.aurion.nhinclib.NullChecker;
import org.alembic.aurion.webserviceproxy.WebServiceProxyHelper;
import gov.hhs.healthit.nhin.XDRAcknowledgementType;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Neil Webb
 */
public class AdapterDocSubmissionDeferredRequestErrorProxyWebServiceUnsecureImpl implements AdapterDocSubmissionDeferredRequestErrorProxy
{
    private Log log = null;
    private static Service cachedService = null;
    private static final String NAMESPACE_URI = "urn:org:alembic:aurion:adapterxdrrequesterror";
    private static final String SERVICE_LOCAL_PART = "AdapterXDRRequestError_Service";
    private static final String PORT_LOCAL_PART = "AdapterXDRRequestError_Port";
    private static final String WSDL_FILE = "AdapterXDRRequestError.wsdl";
    private static final String WS_ADDRESSING_ACTION = "urn:org:alembic:aurion:adapterxdrrequest:XDRRequestErrorInputMessage";
    private WebServiceProxyHelper oProxyHelper = null;

    public AdapterDocSubmissionDeferredRequestErrorProxyWebServiceUnsecureImpl()
    {
        log = createLogger();
        oProxyHelper = createWebServiceProxyHelper();
    }

    protected Log createLogger()
    {
        return LogFactory.getLog(this.getClass());
    }

    protected WebServiceProxyHelper createWebServiceProxyHelper()
    {
        return new WebServiceProxyHelper();
    }

    protected Service getService()
    {
        if (cachedService == null)
        {
            try
            {
                cachedService = oProxyHelper.createService(WSDL_FILE, NAMESPACE_URI, SERVICE_LOCAL_PART);
            }
            catch (Throwable t)
            {
                log.error("Error creating service: " + t.getMessage(), t);
            }
        }
        return cachedService;
    }

    protected AdapterXDRRequestErrorPortType getPort(String url, String wsAddressingAction, AssertionType assertion)
    {
        AdapterXDRRequestErrorPortType port = null;

        Service service = getService();
        if (service != null)
        {
            log.debug("Obtained service - creating port.");

            port = service.getPort(new QName(NAMESPACE_URI, PORT_LOCAL_PART), AdapterXDRRequestErrorPortType.class);
            oProxyHelper.initializeUnsecurePort((javax.xml.ws.BindingProvider) port, url, wsAddressingAction, assertion);
        }
        else
        {
            log.error("Unable to obtain serivce - no port created.");
        }
        return port;
    }

    public XDRAcknowledgementType provideAndRegisterDocumentSetBRequestError(ProvideAndRegisterDocumentSetRequestType request, String errorMessage, AssertionType assertion)
    {
        log.debug("Begin AdapterDocSubmissionDeferredRequestErrorProxyWebServiceUnsecureImpl.provideAndRegisterDocumentSetBRequestError");
        XDRAcknowledgementType response = null;
        String serviceName = NhincConstants.ADAPTER_XDR_ASYNC_REQ_ERROR_SERVICE_NAME;

        try
        {
            log.debug("Before target system URL look up.");
            String url = oProxyHelper.getUrlLocalHomeCommunity(serviceName);
            if (log.isDebugEnabled())
            {
                log.debug("After target system URL look up. URL for service: " + serviceName + " is: " + url);
            }

            if (NullChecker.isNotNullish(url))
            {
                AdapterProvideAndRegisterDocumentSetRequestErrorType wsRequest = new AdapterProvideAndRegisterDocumentSetRequestErrorType();
                wsRequest.setProvideAndRegisterDocumentSetRequest(request);
                wsRequest.setErrorMsg(errorMessage);
                wsRequest.setAssertion(assertion);
                AdapterXDRRequestErrorPortType port = getPort(url, WS_ADDRESSING_ACTION, assertion);
                response = (XDRAcknowledgementType) oProxyHelper.invokePort(port, AdapterXDRRequestErrorPortType.class, "provideAndRegisterDocumentSetBRequestError", wsRequest);
            }
            else
            {
                log.error("Failed to call the web service (" + serviceName + ").  The URL is null.");
            }
        }
        catch (Exception ex)
        {
            log.error("Error: Failed to retrieve url for service: " + serviceName + " for local home community");
            log.error(ex.getMessage(), ex);
        }

        log.debug("End AdapterDocSubmissionDeferredRequestErrorProxyWebServiceUnsecureImpl.provideAndRegisterDocumentSetBRequestError");
        return response;
    }
}
