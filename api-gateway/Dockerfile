FROM java:8-jdk
VOLUME /logs
ADD target/api-gateway*.jar api-gateway.jar
RUN sh -c 'touch /api-gateway.jar'
EXPOSE 8000
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar api-gateway.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
