FROM centos:centos8

ENV WILDFLY_VERSION 22.0.0.Final
ENV WILDFLY_NAME  wildfly-preview
ENV JBOSS_HOME /opt/jboss/wildfly

USER root

RUN echo "[AdoptOpenJDK]" > /etc/yum.repos.d/adoptopenjdk.repo \
 && echo "name=AdoptOpenJDK" >> /etc/yum.repos.d/adoptopenjdk.repo \
 && echo "baseurl=https://adoptopenjdk.jfrog.io/adoptopenjdk/rpm/rhel/7/$(uname -m)" >> /etc/yum.repos.d/adoptopenjdk.repo \
 && echo "enabled=1" >> /etc/yum.repos.d/adoptopenjdk.repo \
 && echo "gpgcheck=1" >> /etc/yum.repos.d/adoptopenjdk.repo \
 && echo "gpgkey=https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public" >> /etc/yum.repos.d/adoptopenjdk.repo

RUN yum update -y && yum -y install adoptopenjdk-11-openj9-jre

# Create a user and group used to launch processes
# The user ID 1000 is the default for the first "regular" user on Fedora/RHEL,
# so there is a high chance that this ID will be equal to the current user
# making it easier to use volumes (no permission issues)
RUN groupadd -r jboss -g 1000 && useradd -u 1000 -r -g jboss -m -d /opt/jboss -s /sbin/nologin -c "JBoss user" jboss && \
    chmod 755 /opt/jboss

RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/$WILDFLY_NAME-$WILDFLY_VERSION.tar.gz \
    && tar xf $WILDFLY_NAME-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/$WILDFLY_NAME-$WILDFLY_VERSION $JBOSS_HOME \
    && rm $WILDFLY_NAME-$WILDFLY_VERSION.tar.gz \
    && chown -R jboss:0 ${JBOSS_HOME} \
    && chmod -R g+rw ${JBOSS_HOME}

#Arquillian Cube uses ss command
RUN yum -y install iproute

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

USER jboss

ENV ADMIN_USER admin
ENV ADMIN_PASS admin

RUN $JBOSS_HOME/bin/add-user.sh -u $ADMIN_USER -p $ADMIN_PASS -g admin --silent

EXPOSE 8080 9990 8787

ENV DRIVER_VERSION 8.0.25
ENV JBOSS_CLI /opt/jboss/wildfly/bin/jboss-cli.sh

RUN   bash -c '$JBOSS_HOME/bin/standalone.sh &' \
      && bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' \
      && curl --location --output /tmp/mysql-connector-java-${DRIVER_VERSION}.jar --url https://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/${DRIVER_VERSION}/mysql-connector-java-${DRIVER_VERSION}.jar \
      && $JBOSS_CLI --connect --command="module add --name=com.mysql --resources=/tmp/mysql-connector-java-${DRIVER_VERSION}.jar --dependencies=javax.api,javax.transaction.api" \
      && $JBOSS_CLI --connect --command="reload" \
      && $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.cj.jdbc.Driver)" \
      && $JBOSS_CLI --connect --command="data-source add --jndi-name=java:/jdbc/personalBudgetDS --jta="false" --name=personal_budgetDS --connection-url=jdbc:mysql://jpa_relations-mysql-cube:3306/personal_budget --driver-name=mysql --user-name=budget --password=budget  --initial-pool-size=1 --min-pool-size=1 --max-pool-size=5 --idle-timeout-minutes=5" \
      && $JBOSS_CLI --connect --command=":shutdown" \
      && rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* \
      && rm -f /tmp/*.jar


CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug", "*:8787"]
