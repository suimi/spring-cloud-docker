FROM java:8-jdk
VOLUME /logs
ADD target/api-gateway*.jar api-gateway.jar
RUN sh -c 'touch /api-gateway.jar'
#时区设置
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/shanghai" > /etc/timezone
EXPOSE 8000
#通过SIGTERM信号，解决容器停止时调用shutdownhook
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar api-gateway.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}
