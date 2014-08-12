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

package org.risbic.intraconnect.jms.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.arjuna.databroker.data.DataFlowNode;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class MockJMSDataFlowNode implements DataFlowNode
{
   private List<Serializable> data;

   public MockJMSDataFlowNode()
   {
      data = new ArrayList<Serializable>();
   }

   @Override
   public String getName()
   {
      return "MockDataFlowNode";
   }

   @Override
   public Map<String, String> getProperties()
   {
      return null;
   }

   public List<Serializable> getData()
   {
      return data;
   }

   public void consume(Serializable obj)
   {
      data.add(obj);
   }
}
