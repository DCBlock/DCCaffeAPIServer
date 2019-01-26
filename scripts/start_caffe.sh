# JAVA
JAVA_HOME=/usr/bin

# APP
APP_PATH=/opt/dccaffe/DCCaffeAPI-0.0.2.jar
CONFIG_PATH=/opt/dccaffe/application.properties

# pinpoint
PINPOINT=/opt/pinpoint-agent/pinpoint-bootstrap-1.8.1.jar
AGENT_ID=3
APPLICATION_NAME=DCCaffeAPI

nohup $JAVA_HOME/java -javaagent:$PINPOINT -Dpinpoint.agentId=$AGENT_ID -Dpinpoint.applicationName=$APPLICATION_NAME  -jar -Djava.net.preferIPv4Stack=true $APP_PATH --spring.config.location=$CONFIG_PATH 1>/var/log/dccaffe/stdout.log 2>/var/log/dccaffe/stderr.log &
#nohup $JAVA_HOME/java -javaagent:$PINPOINT -Dpinpoint.agentId=$AGENT_ID -Dpinpoint.applicationName=$APPLICATION_NAME  -jar -Djava.net.preferIPv4Stack=true /root/dcblock/install/CaffeAPIServer-0.0.1.jar 1>/root/dcblock/logs/stdout.log 2>/root/dcblock/logs/stderr.log &
