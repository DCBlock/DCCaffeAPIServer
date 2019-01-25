# No Redirection
#JAVA_HOME=/root/DcBlock/install/java-se-8u40-ri
#nohup $JAVA_HOME/bin/java -jar /root/DcBlock/install/CaffeAPIServer-0.0.1.jar --spring.config.location=file:/root/DcBlock/application.yml 1>/dev/null 2>&1 &

# stdout, stderr
JAVA_HOME=/usr/bin
APP_PATH=/opt/dccaffe/CaffeAPIServer-0.0.2.jar
CONFIG_PATH=/opt/dccaffe/application.yml

nohup $JAVA_HOME/java -jar -Djava.net.preferIPv4Stack=true $APP_PATH --spring.config.location=$CONFIG_PATH 1>/var/log/dccaffe/stdout.log 2>/var/log/dccaffe/stderr.log &
#nohup $JAVA_HOME/java -jar -Djava.net.preferIPv4Stack=true /root/dcblock/install/CaffeAPIServer-0.0.1.jar 1>/root/dcblock/logs/stdout.log 2>/root/dcblock/logs/stderr.log &
