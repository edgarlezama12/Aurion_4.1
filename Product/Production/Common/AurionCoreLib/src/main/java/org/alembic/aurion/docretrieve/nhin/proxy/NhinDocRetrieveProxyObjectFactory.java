/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package org.alembic.aurion.docretrieve.nhin.proxy;

import org.alembic.aurion.proxy.ComponentProxyObjectFactory;

/**
 * An object factory that uses the Spring Framework to create service
 * implementation objects. The configuration file is referenced in the
 * common properties file location to assist in installation and configuration
 * simplicity.
 *
 * The Spring configuration file is defined by the constant "SPRING_CONFIG_FILE".
 * This file is loaded into an application context in the static initializer
 * and then the objects defined in the config file are available to the framework
 * for creation. This configuration file must be located in the gateway property
 * file directory.
 *
 * To retrieve an object that is created by the framework, the
 * "getBean(String beanId)" method is called on the application context passing
 * in the beanId that is specified in the config file. Considering the default
 * correlation definition in the config file for this component:
 * <bean id="nhindocretrieve" class="org.alembic.aurion.nhindocretrieve.proxy.NhinDocRetrieveNoOpImpl"/>
 * the bean id is "nhindocretrieve" and an object of this type can be retrieved from
 * the application context by calling the getBean method like:
 * context.getBean("nhindocretrieve");. This returns an object that can be casted to
 * the appropriate interface and then used in the application code. See the
 * getNhinDocRetrieveProxy() method in this class.
 *
 * @author Neil Webb
 */
public class NhinDocRetrieveProxyObjectFactory extends ComponentProxyObjectFactory
{
    private static final String CONFIG_FILE_NAME = "NhinDocRetrieveProxyConfig.xml";
    private static final String BEAN_NAME = "nhindocretrieve";

    /**
     * Returns the name of the config file.
     *
     * @return The name of the config file.
     */
    protected String getConfigFileName()
    {
        return CONFIG_FILE_NAME;
    }

    /**
     * Return an instance of the NhinDocRetrieveProxy class.
     *
     * @return An instance of the NhinDocRetrieveProxy class.
     */
    public NhinDocRetrieveProxy getNhinDocRetrieveProxy()
    {
        return getBean(BEAN_NAME, NhinDocRetrieveProxy.class);
    }

}
