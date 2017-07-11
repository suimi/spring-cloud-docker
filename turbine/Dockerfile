FROM java:8-jdk
VOLUME /logs
ADD target/turbine*.jar turbine.jar
RUN sh -c 'touch /turbine.jar'
EXPOSE 8881
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar turbine.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
