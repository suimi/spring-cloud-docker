FROM java:8-jdk
VOLUME /logs
ADD target/service-a*.jar service-a.jar
RUN sh -c 'touch /service-a.jar'
#时区设置
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
EXPOSE 8010
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar service-a.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}" ]
