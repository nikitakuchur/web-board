FROM jboss/wildfly:24.0.0.Final

ADD config/standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
ADD target/backend.war /opt/jboss/wildfly/standalone/deployments/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
