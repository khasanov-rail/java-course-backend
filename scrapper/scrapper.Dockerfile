FROM openjdk:21
LABEL authors="RRKh"
COPY ./target/scrapper.jar scrapper.jar

ENTRYPOINT ["java", "-jar", "/scrapper.jar"]
