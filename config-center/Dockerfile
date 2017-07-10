FROM java:8-jdk
VOLUME /logs
ADD target/config-center*.jar config-center.jar
RUN sh -c 'touch /config-center.jar'
EXPOSE 9000
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar config-center.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}" ]
