/*
 * Copyright 2005-2014 Red Hat, Inc.
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.risbic.intraconnect.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import java.io.Serializable;
import java.util.UUID;

import com.arjuna.databroker.data.DataFlowNode;
import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class JMSDataProviderImpl<T extends Serializable> extends AbstractJMSDataTransport implements JMSDataProvider<T>
{
   public static final String DATA_FLOW_NODE_ID = "DATA_FLOW_NODE_ID";

   private static final Logger logger = Logger.getLogger(JMSDataProviderImpl.class.getName());

   private MessageProducer producer;

   private UUID id;

   /**
    * Creates a new JMSDataProvider.  This data provider should be backed by a Durable Topic to which consumers will
    * be dynamically subscribed.  Message filters are used to send the correct messages to the Data Consumers.  The
    * filter is currently based on the dfNode name.
    */
   public JMSDataProviderImpl(ConnectionFactory connectionFactory, Destination destination, String username, String password)
   {
      super(connectionFactory, destination, username, password);
      id = UUID.randomUUID();
   }

   @Override
   public void produce(T data)
   {
      try
      {
         Message message = session.createObjectMessage(data);
         message.setStringProperty(DATA_FLOW_NODE_ID, id.toString());
         producer.send(message);
      }
      catch(Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   @Override
   public UUID getId()
   {
      return id;
   }

   @Override
   public DataFlowNode getDataFlowNode()
   {
      return null;
   }

   // Life Cycle Methods
   @Override
   public void start() throws JMSException
   {
      super.start();
      producer = session.createProducer(destination);
      connection.start();
   }

   @Override
   public void stop() throws JMSException
   {
      producer.close();
      session.unsubscribe(destination.toString());
      super.stop();
   }

   @Override
   public void destroy() throws JMSException
   {
      producer = null;
      super.destroy();
   }

   @Override
   public void pause()
   {

   }
}
