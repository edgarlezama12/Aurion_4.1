/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package org.alembic.aurion.patientdiscovery.entity.deferred.request.queue;

import org.alembic.aurion.common.nhinccommon.AcknowledgementType;
import org.alembic.aurion.common.nhinccommon.AssertionType;
import org.alembic.aurion.common.nhinccommon.NhinTargetCommunitiesType;
import org.alembic.aurion.common.nhinccommon.NhinTargetSystemType;
import org.alembic.aurion.connectmgr.ConnectionManagerCache;
import org.alembic.aurion.connectmgr.ConnectionManagerException;
import org.alembic.aurion.connectmgr.data.CMUrlInfos;
import org.alembic.aurion.nhinclib.NhincConstants;
import org.alembic.aurion.nhinclib.NullChecker;
import org.alembic.aurion.patientdiscovery.passthru.deferred.response.proxy.PassthruPatientDiscoveryDeferredRespProxy;
import org.alembic.aurion.patientdiscovery.passthru.deferred.response.proxy.PassthruPatientDiscoveryDeferredRespProxyObjectFactory;
import org.alembic.aurion.patientdiscovery.PatientDiscovery201305Processor;
import org.alembic.aurion.patientdiscovery.PatientDiscoveryAuditLogger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201306UV02;
import org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType;

/**
 *
 * @author JHOPPESC
 */
public class EntityPatientDiscoveryDeferredReqQueueOrchImpl {
    private static Log log = LogFactory.getLog(EntityPatientDiscoveryDeferredReqQueueOrchImpl.class);

    public MCCIIN000002UV01 addPatientDiscoveryAsyncReq(PRPAIN201305UV02 request, AssertionType assertion, NhinTargetCommunitiesType targets) {
        RespondingGatewayPRPAIN201305UV02RequestType unsecureRequest = new RespondingGatewayPRPAIN201305UV02RequestType();
        unsecureRequest.setAssertion(assertion);
        unsecureRequest.setNhinTargetCommunities(targets);
        unsecureRequest.setPRPAIN201305UV02(request);

        // Audit the incoming Nhin 201305 Message
        PatientDiscoveryAuditLogger auditLogger = new PatientDiscoveryAuditLogger();
        AcknowledgementType ack = auditLogger.auditEntity201305(unsecureRequest, unsecureRequest.getAssertion(), NhincConstants.AUDIT_LOG_INBOUND_DIRECTION);

        MCCIIN000002UV01 resp = addPatientDiscoveryAsyncReq(unsecureRequest);

        // Audit the responding 201306 Message
        ack = auditLogger.auditAck(resp, unsecureRequest.getAssertion(), NhincConstants.AUDIT_LOG_OUTBOUND_DIRECTION, NhincConstants.AUDIT_LOG_ENTITY_INTERFACE);

        return resp;
    }

    protected MCCIIN000002UV01 addPatientDiscoveryAsyncReq(RespondingGatewayPRPAIN201305UV02RequestType request) {
        MCCIIN000002UV01 ack = new MCCIIN000002UV01();

        // TODO: Add code here to "process" the request and send a response out to the Nhin.
        PatientDiscovery201305Processor msgProcessor = new PatientDiscovery201305Processor();
        PRPAIN201306UV02 resp = msgProcessor.process201305(request.getPRPAIN201305UV02(), request.getAssertion());

        ack = sendToNhin(resp, request.getAssertion(), request.getNhinTargetCommunities());

        return ack;
    }

    protected MCCIIN000002UV01 sendToNhin(PRPAIN201306UV02 respMsg, AssertionType assertion, NhinTargetCommunitiesType targets) {
        MCCIIN000002UV01 resp = new MCCIIN000002UV01();
        NhinTargetSystemType targetSystem = new NhinTargetSystemType();
        CMUrlInfos urlInfoList = null;

        if (targets != null) {
            urlInfoList = getTargets(targets);
        }

        if (urlInfoList != null &&
                NullChecker.isNotNullish(urlInfoList.getUrlInfo()) &&
                urlInfoList.getUrlInfo().get(0) != null &&
                NullChecker.isNotNullish(urlInfoList.getUrlInfo().get(0).getUrl())) {

            targetSystem.setUrl(urlInfoList.getUrlInfo().get(0).getUrl());

            PassthruPatientDiscoveryDeferredRespProxyObjectFactory patientDiscoveryFactory = new PassthruPatientDiscoveryDeferredRespProxyObjectFactory();
            PassthruPatientDiscoveryDeferredRespProxy proxy = patientDiscoveryFactory.getPassthruPatientDiscoveryDeferredRespProxy();

            resp = proxy.proxyProcessPatientDiscoveryAsyncResp(respMsg, assertion, targetSystem);

        }

        return resp;
    }

    protected CMUrlInfos getTargets(NhinTargetCommunitiesType targetCommunities) {
        CMUrlInfos urlInfoList = null;

        // Obtain all the URLs for the targets being sent to
        try {
            urlInfoList = ConnectionManagerCache.getEndpontURLFromNhinTargetCommunities(targetCommunities, NhincConstants.PATIENT_DISCOVERY_ASYNC_RESP_SERVICE_NAME);
        } catch (ConnectionManagerException ex) {
            log.error("Failed to obtain target URLs for service " + NhincConstants.PATIENT_DISCOVERY_ASYNC_RESP_SERVICE_NAME);
            return null;
        }

        return urlInfoList;
    }

}
