FROM java:8-jdk
VOLUME /logs
ADD target/eureka-server*.jar eureka-server.jar
RUN sh -c 'touch /eureka-server.jar'
#时区设置
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/shanghai" > /etc/timezone
EXPOSE 9999
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar eureka-server.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}
