FROM java:8-jdk
VOLUME /logs
ADD target/config-center*.jar config-center.jar
RUN sh -c 'touch /config-center.jar'
#时区设置
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/shanghai" > /etc/timezone
EXPOSE 9000
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar config-center.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}

