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
package org.alembic.aurion.hiem.dte.marshallers;

import org.w3c.dom.Element;
import org.alembic.aurion.common.nhinccommonadapter.SubscribeRequestType;

/**
 *
 * @author rayj
 */
public class AdapterSubscribeRequestMarshaller {

    private static final String ContextPath = "org.alembic.aurion.common.nhinccommonadapter";

    public Element marshalSubscribeRequest(SubscribeRequestType object) {
        return new Marshaller().marshal(object, ContextPath);
    }

    public SubscribeRequestType unmarshalSubscribeRequest(Element element) {
        return (SubscribeRequestType) new Marshaller().unmarshal(element, ContextPath);
    }
}
