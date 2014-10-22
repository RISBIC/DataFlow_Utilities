/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England;
 *                     Newcastle University, Newcastle-upon-Tyne, England;
 *                     Red Hat Middleware LLC, Newcastle-upon-Tyne, England. All rights reserved.
 */

package org.risbic.transport.jms;

import java.io.Serializable;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.jee.DataProviderFactory;

public class JMSDataProviderFactory implements DataProviderFactory
{
    @SuppressWarnings("unchecked")
    public <T> DataProvider<T> createDataProvider(DataFlowNode dataFlowNode, Class<T> dataClass)
        throws Exception
    {
    	if (dataClass.isAssignableFrom(Serializable.class))
    	    return (DataProvider<T>) new JMSDataProviderImpl<Serializable>();
    	else
    		return null;
    }
}