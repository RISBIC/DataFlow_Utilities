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

package org.risbic.transport.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.arjuna.databroker.data.DataFlowNode;
import com.arjuna.databroker.data.connector.ReferrerDataConsumer;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class JMSDataConsumerImpl<T extends Serializable> extends AbstractJMSDataTransport<T> implements ReferrerDataConsumer<T>
{
   private static final Logger logger = Logger.getLogger(JMSDataConsumerImpl.class.getName());

   private MessageConsumer consumer;

   private DataFlowNode dfNode;

   private String methodName;

   public JMSDataConsumerImpl(DataFlowNode dfNode, String methodName)
   {
      this.dfNode = dfNode;
      this.methodName = methodName;
   }

   @Override
   public Class<?> getNameClass()
   {
       return String.class;
   }

   @Override
   public <N> void addReferredTo(N name)
   {
       if (name instanceof String)
       {
           // TODO
           logger.log(Level.SEVERE, "Unimplemented operation: addReferredTo");
       }
       else
           logger.log(Level.WARNING, "Unsupported name format: \"" + name + "\"");
   }

   @Override
   public <N> void removeReferredTo(N name)
   {
       if (name instanceof String)
       {
           // TODO
           logger.log(Level.SEVERE, "Unimplemented operation: removeReferredTo");
       }
       else
           logger.log(Level.WARNING, "Unsupported name format: \"" + name + "\"");
   }

   @Override
   public DataFlowNode getDataFlowNode()
   {
      return null;
   }

   @Override
   public void start() throws JMSException
   {
      super.start();
      consumer = session.createConsumer(destination);
      MessageListener messageListener = new MessageHandler();
      consumer.setMessageListener(messageListener);
      connection.start();
   }

   @Override
   public void stop() throws JMSException
   {
      consumer.close();
      session.unsubscribe(destination.toString());
      super.stop();
   }

   @Override
   public void destroy() throws JMSException
   {
      consumer = null;
      super.destroy();
   }

   @Override
   public void pause()
   {
      // Block the Message Handler?
   }

   private class MessageHandler implements MessageListener
   {
      private MessageHandler() {};

      @Override
      public void onMessage(Message message)
      {
         try
         {
            if (message instanceof ObjectMessage)
            {
               Object data = ((ObjectMessage) message).getObject();
               Util.invokeMethod(dfNode, methodName, data);
            }
         }
         catch(Exception e)
         {
            throw new RuntimeException(e);
         }
      }
   }
}
