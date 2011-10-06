/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.alembic.aurion.hiem.entity.unsubscribe.proxy;

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
 * for creation. This configuration file can be located anywhere in the
 * classpath.
 *
 * To retrieve an object that is created by the framework, the
 * "getBean(String beanId)" method is called on the application context passing
 * in the beanId that is specified in the config file. Considering the default
 * correlation definition in the config file for this component:
 * <bean id="entityhiemunsubscribe" class="org.alembic.aurion.hiem.entity.unsubscribe.proxy.EntityUnsubscribeProxyNoOpImpl"/>
 * the bean id is "entityhiemunsubscribe" and an object of this type can be retrieved from
 * the application context by calling the getBean method like:
 * context.getBean("entityhiemunsubscribe");. This returns an object that can be casted to
 * the appropriate interface and then used in the application code. See the
 * getPassthruUnsubscribeProxy() method in this class.
 *
 * @author Sai Valluripalli
 */
public class EntityHiemUnsubscribeProxyObjectFactory extends ComponentProxyObjectFactory {
    private static final String CONFIG_FILE_NAME = "HiemProxyConfig.xml";
    private static final String BEAN_NAME = "entityhiemunsubscribe";

    protected String getConfigFileName() {
        return CONFIG_FILE_NAME;
    }

    /**
     *
     * @return EntitySubscribeProxy
     */
    public EntityHiemUnsubscribeProxy getPassthruUnsubscribeProxy()
    {
        return getBean(BEAN_NAME, EntityHiemUnsubscribeProxy.class);
    }

}
