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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.After;

/**
 * @author <a href="mailto:mtaylor@redhat.com">Martyn Taylor</a>
 */

public abstract class AbstractJMSDataTransportTest extends TestCase
{
   protected ConnectionFactory connectionFactory;

   protected Connection connection;

   protected Session session;

   protected Topic topic;

   public void setUp() throws NamingException, JMSException, Exception
   {
      Properties jndiProperties = new Properties();
      jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
      jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
      jndiProperties.put(Context.SECURITY_PRINCIPAL, "jms");
      jndiProperties.put(Context.SECURITY_CREDENTIALS, "password");
      Context initialContext = new InitialContext(jndiProperties);

      connectionFactory = (ConnectionFactory) initialContext.lookup("jms/RemoteConnectionFactory");
      connection = connectionFactory.createConnection("jms", "password");
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      topic = (Topic) initialContext.lookup("jms/topic/risbic");
   }

   @After
   public void tearDown() throws JMSException
   {
      session.close();
      connection.close();
   }
}
