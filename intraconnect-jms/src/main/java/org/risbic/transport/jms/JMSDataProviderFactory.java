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
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.DataProvider;
import com.arjuna.databroker.data.connector.NamedDataProvider;
import com.arjuna.databroker.data.jee.DataProviderFactory;

public class JMSDataProviderFactory implements DataProviderFactory
{
    private static final Logger logger = Logger.getLogger(JMSDataProviderFactory.class.getName());

    @SuppressWarnings("unchecked")
    public <T> DataProvider<T> createDataProvider(DataFlowNode dataFlowNode, Type dataProviderType)
        throws Exception
    {
        logger.log(Level.FINE, "JMSDataProviderFactory.createDataProvider: " + dataProviderType);

        if (dataProviderType instanceof ParameterizedType)
        {
            ParameterizedType dataProviderParameterizedType = (ParameterizedType) dataProviderType;

            if (NamedDataProvider.class.isAssignableFrom((Class<?>) dataProviderParameterizedType.getRawType()))
                if ((dataProviderParameterizedType.getActualTypeArguments().length == 1) && Serializable.class.isAssignableFrom((Class<?>) dataProviderParameterizedType.getActualTypeArguments()[0]))
                    return (DataProvider<T>) new JMSDataProviderImpl<Serializable>();
        }

        return null;
    }
}
