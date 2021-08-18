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

COPY mysql-connector-java-8.0.25.jar $JBOSS_HOME/modules/system/layers/base/com/mysql/main/
COPY module.xml $JBOSS_HOME/modules/system/layers/base/com/mysql/main/
COPY standalone.xml $JBOSS_HOME/standalone/configuration/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug", "*:8787"]
