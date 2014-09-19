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

import java.io.Serializable;
import java.lang.reflect.Method;
import com.arjuna.databroker.data.DataFlowNode;
import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public class Util
{
   private static final Logger logger = Logger.getLogger(Util.class.getName());

   public static void invokeMethod(DataFlowNode dfNode, String methodName, Object data)
   {
      try
      {
         getMethod(dfNode.getClass(), methodName).invoke(dfNode, data);
      }
      catch (Throwable throwable)
      {
         logger.warn("Problem invoking consumer", throwable);
         throw new RuntimeException("Could not invoke method: " + methodName + " on Class: " +
                                       dfNode.getClass(), throwable);
      }
   }

   public static Method getMethod(Class<?> nodeClass, String nodeMethodName)
   {
      try
      {
         return nodeClass.getMethod(nodeMethodName, Serializable.class);
      }
      catch (Throwable throwable)
      {
         logger.warn("Unable to find method \"" + nodeMethodName + "\"", throwable);
         throw new RuntimeException("Unable to find metohd", throwable);
      }
   }
}
