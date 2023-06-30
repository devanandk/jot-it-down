FROM openjdk:17-jdk-alpine
VOLUME /opt
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/*.jar notesapp.jar
EXPOSE 8080
#ENTRYPOINT exec java $JAVA_OPTS -jar notesapp.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar notesapp.jar
