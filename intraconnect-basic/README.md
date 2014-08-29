Running JMS Data Transport Tests
================================

Download and Install WildFly 8
-------------------------------

1. Download WildFly here: http://wildfly.org/downloads/
2. Decompress to your desired folder
3. Set JBOSS_HOME=<path to wildfly dir>

Configure WildFly
-----------------

1. Add new user

```bash
  cd $JBOSS_HOME
  ./bin/add_user.sh

  What type of user do you wish to add?
   a) Management User (mgmt-users.properties)
   b) Application User (application-users.properties)
  (a): a

  Username : jms
  Password : password

  About to add user 'jms' for realm 'ManagementRealm'
  Is this correct yes/no? yes

  Is this new user going to be used for one AS process to connect to another AS process?
  e.g. for a slave host controller connecting to the master or for a Remoting connection for server to server EJB calls.
  yes/no? no
```

2. Add Risbic Topic

Add the following XML snippet to $JBOSS_HOME/standalone/configuration/standalone-full.xml as a child element of <jms-destinations>

```xml
  <jms-topic name="risbic">
    <entry name="java:/jboss/exported/jms/topic/risbic"/>
  </jms-topic>
```

Start Wildfly
--------------

```bash
  cd $JBOSS_HOME
  ./bin/standalone.sh -c standalone-full.xml
```

Run Tests
----------

```bash
  cd <intraconnect/tests/ dir>
  mvn clean test
```
