FROM java:8-jdk
VOLUME /logs
ADD target/service-b*.jar service-b.jar
RUN sh -c 'touch /service-b.jar'
#时区设置
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/shanghai" > /etc/timezone
EXPOSE 8020
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar service-b.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}" ]
