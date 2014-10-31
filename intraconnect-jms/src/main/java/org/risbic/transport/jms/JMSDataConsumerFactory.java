/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England;
 *                     Newcastle University, Newcastle-upon-Tyne, England;
 *                     Red Hat Middleware LLC, Newcastle-upon-Tyne, England. All rights reserved.
 */

package org.risbic.transport.jms;

import java.io.Serializable;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.jee.DataConsumerFactory;

public class JMSDataConsumerFactory implements DataConsumerFactory
{
    @SuppressWarnings("unchecked")
    public <T> DataConsumer<T> createDataConsumer(DataFlowNode dataFlowNode, String methodName, Class<T> dataClass)
        throws Exception
    {
        if (Serializable.class.isAssignableFrom(dataClass))
            return (DataConsumer<T>) new JMSDataConsumerImpl<Serializable>(dataFlowNode, methodName);
        else
            return null;
    }
}
