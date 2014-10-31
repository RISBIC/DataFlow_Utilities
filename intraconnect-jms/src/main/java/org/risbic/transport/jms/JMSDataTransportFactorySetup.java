/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England;
 *                     Newcastle University, Newcastle-upon-Tyne, England;
 *                     Red Hat Middleware LLC, Newcastle-upon-Tyne, England. All rights reserved.
 */

package org.risbic.transport.jms;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import com.arjuna.databroker.data.jee.DataConsumerFactoryInventory;
import com.arjuna.databroker.data.jee.DataProviderFactoryInventory;

@Startup
@Singleton
public class JMSDataTransportFactorySetup
{
    @PostConstruct
    public void setupTransportFactories()
    {
        _jmsDataConsumerFactory = new JMSDataConsumerFactory();
        _jmsDataProviderFactory = new JMSDataProviderFactory();

        _dataConsumerFactoryInventory.addDataConsumerFactory(_jmsDataConsumerFactory);
        _dataProviderFactoryInventory.addDataProviderFactory(_jmsDataProviderFactory);
    }

    @PreDestroy
    public void cleanupTransportFactories()
    {
        _dataConsumerFactoryInventory.removeDataConsumerFactory(_jmsDataConsumerFactory);
        _dataProviderFactoryInventory.removeDataProviderFactory(_jmsDataProviderFactory);
    }

    private JMSDataConsumerFactory _jmsDataConsumerFactory;
    private JMSDataProviderFactory _jmsDataProviderFactory;

    @EJB(lookup="java:global/databroker/data-common-jee/DataConsumerFactoryInventory")
    private DataConsumerFactoryInventory _dataConsumerFactoryInventory;
    @EJB(lookup="java:global/databroker/data-common-jee/DataProviderFactoryInventory")
    private DataProviderFactoryInventory _dataProviderFactoryInventory;
}
