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

package org.risbic.transport.jms.mock;

import javax.jms.JMSException;
import java.util.UUID;

import com.arjuna.databroker.data.DataFlowNode;
import org.risbic.transport.jms.JMSDataProvider;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class MockJMSDataProvider implements JMSDataProvider
{
   @Override
   public UUID getId()
   {
      return null;
   }

   @Override
   public DataFlowNode getDataFlowNode()
   {
      return null;
   }

   @Override
   public void produce(Object data)
   {
   }

   @Override
   public void start() throws JMSException
   {

   }

   @Override
   public void stop() throws JMSException
   {

   }

   @Override
   public void pause()
   {

   }

   @Override
   public void restart() throws JMSException
   {

   }

   @Override
   public void destroy() throws JMSException
   {

   }
}
