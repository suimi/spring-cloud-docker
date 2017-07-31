FROM java:8-jdk
VOLUME /logs
ADD target/monitor-dashboard*.jar monitor-dashboard.jar
RUN sh -c 'touch /monitor-dashboard.jar'
#时区设置
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/shanghai" > /etc/timezone
EXPOSE 8880
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar monitor-dashboard.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}
