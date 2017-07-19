FROM java:8-jdk
VOLUME /logs
ADD target/hystrix-dashboard*.jar hystrix-dashboard.jar
RUN sh -c 'touch /hystrix-dashboard.jar'
#时区设置
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
EXPOSE 8882
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar hystrix-dashboard.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
