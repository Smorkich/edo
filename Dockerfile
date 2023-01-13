FROM openjdk:11-ea-17-jre-slim

ENV SPRING_DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/edo_db"
ENV SPRING_DATASOURCE_USERNAME="postgres"
ENV SPRING_DATASOURCE_PASSWORD="oog"

WORKDIR /opt/dom-assistant
COPY target/kcl-rest-application-1.0.jar /opt/dom-assistant/jar/kcl-rest-application.jar
COPY src/main/resources/files-to-load /opt/dom-assistant/resources

COPY run.sh /opt/dom-assistant/run.sh
#ENTRYPOINT ["/opt/dom-assistant/run.sh"]
RUN env
ENTRYPOINT ["sh", "-c", "java -jar /opt/dom-assistant/jar/kcl-rest-application.jar"]