FROM jboss/wildfly:21.0.0.Final

ADD standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml

ADD target/web-board.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
