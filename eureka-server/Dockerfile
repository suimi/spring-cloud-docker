FROM java:8-jdk
VOLUME /logs
ADD target/eureka-server*.jar eureka-server.jar
RUN sh -c 'touch /eureka-server.jar'
EXPOSE 9999
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar eureka-server.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
