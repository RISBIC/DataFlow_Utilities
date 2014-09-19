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
import javax.jms.MessageProducer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.risbic.transport.jms.mock.MockJMSDataFlowNode;
import org.risbic.transport.jms.mock.MockJMSDataProvider;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class JMSDataConsumerImplTest extends AbstractJMSDataTransportTest
{
   private MessageProducer producer;

   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      producer = session.createProducer(topic);
   }

   @Test
   public void testConsume() throws JMSException, InterruptedException
   {
      JMSDataProvider provider = new MockJMSDataProvider();
      MockJMSDataFlowNode dfNode = new MockJMSDataFlowNode();
      String methodName = "consume";
      Class clazz = MockJMSDataFlowNode.class;

      JMSDataConsumerImpl dataConsumer = new JMSDataConsumerImpl(dfNode, methodName, clazz, connectionFactory, topic, provider, "jms", "password");
      dataConsumer.start();

      String message = "Test Message";
      List<Serializable> messages = new ArrayList<Serializable>();
      for(int i=0; i<10; i++)
      {
         messages.add(message);
      }
      sendMessages(messages);

      // Wait for messages to arrive
      Thread.sleep(2000);
      Assert.assertEquals(messages, dfNode.getData());
   }

   public void sendMessages(List<Serializable> messages) throws JMSException
   {
      for (Serializable message : messages)
      {
         producer.send(session.createObjectMessage(message));
      }
   }
}
