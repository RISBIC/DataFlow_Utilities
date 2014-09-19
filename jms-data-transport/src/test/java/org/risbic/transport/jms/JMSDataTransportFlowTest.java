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

import java.util.ArrayList;
import java.util.List;

import org.risbic.transport.jms.mock.MockJMSDataFlowNode;

import org.junit.Before;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class JMSDataTransportFlowTest extends AbstractJMSDataTransportTest
{
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
   }

   public void testJMSDataProviderToJMSDataConsumerFlow() throws Exception
   {
      MockJMSDataFlowNode consumingNode = new MockJMSDataFlowNode();

      JMSDataProvider dataProvider = new JMSDataProviderImpl(connectionFactory, topic, "jms","password");
      JMSDataConsumerImpl dataConsumer = new JMSDataConsumerImpl(consumingNode, "consume", MockJMSDataFlowNode.class,
                                                                 connectionFactory, topic, dataProvider, "jms", "password");

      dataProvider.start();
      dataConsumer.start();

      List<String> messages = new ArrayList<String>();
      for (int i=0; i<10; i++)
      {
         String message = "Test Message: " + i;
         messages.add(message);
         dataProvider.produce(message);
      }

      // Wait for messages to be received.
      Thread.sleep(2000);
      assertEquals(messages, consumingNode.getData());
   }
}
