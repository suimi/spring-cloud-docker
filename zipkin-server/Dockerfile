FROM java:8-jdk
VOLUME /logs
ADD target/zipkin-server*.jar zipkin-server.jar
RUN sh -c 'touch /zipkin-server.jar'
EXPOSE 8883
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar zipkin-server.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
