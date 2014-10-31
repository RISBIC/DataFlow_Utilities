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
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.risbic.transport.jms.mock.MockJMSDataFlowNode;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class JMSDataProviderImplTest extends AbstractJMSDataTransportTest
{
   private MessageConsumer consumer;

   private TestMessageListener messageListener;

   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      consumer = session.createConsumer(topic);
      messageListener = new TestMessageListener();
      consumer.setMessageListener(messageListener);
      connection.start();
   }

   @Test
   public void testConsume() throws JMSException, InterruptedException
   {
      MockJMSDataFlowNode dfNode = new MockJMSDataFlowNode();

      JMSDataProviderImpl provider = new JMSDataProviderImpl();
      provider.start();

      String message = "Test Message";
      List<Serializable> messages = new ArrayList<Serializable>();
      for(int i=0; i<10; i++)
      {
         provider.produce(message);
         messages.add(message);
      }

      // Wait for messages to arrive to message listener.
      Thread.sleep(2000);
      assertEquals(messages, messageListener.getMessages());
   }

   private class TestMessageListener implements MessageListener
   {
      private List<String> messages;

      public TestMessageListener()
      {
         messages = new ArrayList<String>();
      }

      @Override
      public void onMessage(Message message)
      {
         try
         {
            ObjectMessage objectMessage = (ObjectMessage) message;
            String messageContent = (String) objectMessage.getObject();
            messages.add(messageContent);
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      }

      public List<String> getMessages()
      {
         return messages;
      }
   }
}
