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

package universalclientgui;

import org.alembic.aurion.common.nhinccommon.HomeCommunityType;
import org.alembic.aurion.common.nhinccommon.NhinTargetCommunitiesType;
import org.alembic.aurion.common.nhinccommon.NhinTargetCommunityType;
import org.alembic.aurion.common.nhinccommonentity.RespondingGatewayCrossGatewayRetrieveRequestType;
import org.alembic.aurion.connectmgr.ConnectionManagerCache;
import org.alembic.aurion.connectmgr.ConnectionManagerException;
import org.alembic.aurion.entitydocretrieve.EntityDocRetrieve;
import org.alembic.aurion.entitydocretrieve.EntityDocRetrievePortType;
import org.alembic.aurion.nhinclib.NhincConstants;
import org.alembic.aurion.properties.PropertyAccessException;
import org.alembic.aurion.properties.PropertyAccessor;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequestType.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponseType.DocumentResponse;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author patlollav
 */
public class DocumentRetrieveClient {
    private static final String PROPERTY_FILE_NAME = "gateway";
    private static final String PROPERTY_LOCAL_HOME_COMMUNITY = "localHomeCommunityId";
    private static Log log = LogFactory.getLog(DocumentRetrieveClient.class);
    private static EntityDocRetrieve service = new EntityDocRetrieve();

    public String retriveDocument(DocumentInformation documentInformation){

        EntityDocRetrievePortType port = getPort(getEntityDocumentRetrieveProxyAddress());

        RespondingGatewayCrossGatewayRetrieveRequestType request = createCrossGatewayRetrieveRequest(documentInformation);


        RetrieveDocumentSetResponseType response = port.respondingGatewayCrossGatewayRetrieve(request);

        return extractDocument(response);
    }

    private String extractDocument(RetrieveDocumentSetResponseType response){
        String documentInXmlFormat = null;

        if (response == null)
        {
            return null;
        }
        List<DocumentResponse> documentResponseList = response.getDocumentResponse();

        if (documentResponseList != null && documentResponseList.size() > 0)
        {
            DocumentResponse documentResponse = documentResponseList.get(0);

            if (documentResponse != null && documentResponse.getDocument() != null)
            {
                documentInXmlFormat = new String(documentResponse.getDocument());
            }
        }
        
        //log.debug("Document: " + documentInXmlFormat);

        //convertXMLToHTML(documentInXmlFormat, null);

        return documentInXmlFormat;
    }

    /**
     * 
     * @return
     */
    private RespondingGatewayCrossGatewayRetrieveRequestType createCrossGatewayRetrieveRequest(DocumentInformation documentInformation){
        
        RespondingGatewayCrossGatewayRetrieveRequestType request = new RespondingGatewayCrossGatewayRetrieveRequestType();

        RetrieveDocumentSetRequestType retrieveDocumentSetRequest = new RetrieveDocumentSetRequestType();

        DocumentRequest docRequest = new DocumentRequest();
        //docRequest.setHomeCommunityId("urn:oid:2.2");
        //docRequest.setRepositoryUniqueId("1");
        docRequest.setHomeCommunityId(documentInformation.getHomeCommunityID());
        docRequest.setRepositoryUniqueId(documentInformation.getRepositoryUniqueID());
        docRequest.setDocumentUniqueId(documentInformation.getDocumentID());

        retrieveDocumentSetRequest.getDocumentRequest().add(docRequest);

        request.setRetrieveDocumentSetRequest(retrieveDocumentSetRequest);

        // Add Assertion
        AssertionCreator assertionCreator = new AssertionCreator();
        request.setAssertion(assertionCreator.createAssertion());

        request.setNhinTargetCommunities(createTargetCommunities(documentInformation.getHomeCommunityID()));
        return request;
    }

    private NhinTargetCommunitiesType createTargetCommunities(String homeCommunityId)
    {
        NhinTargetCommunitiesType result = new NhinTargetCommunitiesType();
        NhinTargetCommunityType community = new NhinTargetCommunityType();

        HomeCommunityType hc = new HomeCommunityType();
        hc.setHomeCommunityId(homeCommunityId);

        community.setHomeCommunity(hc);

        result.getNhinTargetCommunity().add(community);

        return result;

    }
    /**
     *
     * @return
     */
    private String getEntityDocumentRetrieveProxyAddress() {
        String endpointAddress = null;

        try {
            // Lookup home community id
            String homeCommunity = getHomeCommunityId();
            // Get endpoint url
            endpointAddress = ConnectionManagerCache.getEndpointURLByServiceName(homeCommunity, NhincConstants.ENTITY_DOC_RETRIEVE_PROXY_SERVICE_NAME);
            log.debug("Doc Retrive endpoint address: " + endpointAddress);
        } catch (PropertyAccessException pae) {
            log.error("Exception encountered retrieving the local home community: " + pae.getMessage(), pae);
        } catch (ConnectionManagerException cme) {
            log.error("Exception encountered retrieving the entity doc query connection endpoint address: " + cme.getMessage(), cme);
        }
        return endpointAddress;
    }

    /**
     *
     * @param url
     * @return
     */
    private EntityDocRetrievePortType getPort(String url) {
        if (service == null) {
            service = new EntityDocRetrieve();
        }

        EntityDocRetrievePortType port = service.getEntityDocRetrievePortSoap();

        ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        return port;
    }

    /**
     * Retrieve the local home community id
     *
     * @return Local home community id
     * @throws org.alembic.aurion.properties.PropertyAccessException
     */
    private String getHomeCommunityId() throws PropertyAccessException {
        return PropertyAccessor.getProperty(PROPERTY_FILE_NAME, PROPERTY_LOCAL_HOME_COMMUNITY);
    }



}
