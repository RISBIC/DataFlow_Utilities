/*
 * Copyright (c) 2014, Arjuna Technologies Limited, Newcastle-upon-Tyne, England;
 *                     Newcastle University, Newcastle-upon-Tyne, England;
 *                     Red Hat Middleware LLC, Newcastle-upon-Tyne, England. All rights reserved.
 */

package org.risbic.transport.jms;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataConsumer;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.connector.ReferrerDataConsumer;
import com.arjuna.databroker.data.jee.DataConsumerFactory;

public class JMSDataConsumerFactory implements DataConsumerFactory
{
    private static final Logger logger = Logger.getLogger(JMSDataConsumerFactory.class.getName());

    @SuppressWarnings("unchecked")
    public <T> DataConsumer<T> createDataConsumer(DataFlowNode dataFlowNode, String methodName, Type dataConsumerType)
        throws Exception
    {
        logger.log(Level.FINE, "JMSDataConsumerFactory.createDataConsumer: " + methodName + ", " + dataConsumerType);

        if (dataConsumerType instanceof ParameterizedType)
        {
            ParameterizedType dataConsumerParameterizedType = (ParameterizedType) dataConsumerType;

            if (dataConsumerParameterizedType.getRawType() instanceof ReferrerDataConsumer)
                if ((dataConsumerParameterizedType.getActualTypeArguments().length == 1) && (dataConsumerParameterizedType.getActualTypeArguments()[0] instanceof Serializable))
                    return (DataConsumer<T>) new JMSDataConsumerImpl<Serializable>(dataFlowNode, methodName);
        }

        return null;
    }
}
