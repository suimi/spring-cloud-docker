FROM java:8-jdk
VOLUME /logs
ADD target/monitor-dashboard*.jar monitor-dashboard.jar
RUN sh -c 'touch /monitor-dashboard.jar'
EXPOSE 8880
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar monitor-dashboard.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
