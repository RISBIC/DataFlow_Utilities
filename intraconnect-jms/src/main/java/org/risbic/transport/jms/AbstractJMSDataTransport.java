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

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import java.io.Serializable;

import org.jboss.logging.Logger;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public abstract class AbstractJMSDataTransport<T extends Serializable> implements JMSDataTransportLifeCycle
{
   private static final Logger logger = Logger.getLogger(AbstractJMSDataTransport.class.getName());

   protected Connection connection;

   protected Session session;

   protected Destination destination;

   protected String username;

   protected String password;

   @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
   private static ConnectionFactory connectionFactory;

   @Resource(lookup = "jms/topic/risbic")
   private static Topic topic;

   public AbstractJMSDataTransport()
   {
   }

   @Override
   public void start() throws JMSException
   {
      connection = connectionFactory.createConnection(username, password);
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
   }

   @Override
   public void stop() throws JMSException
   {
      session.close();
      connection.close();
   }

   @Override
   public void destroy() throws JMSException
   {
      session = null;
      connection = null;
   }

   @Override
   public void restart() throws JMSException
   {
      stop();
      start();
   }
}
