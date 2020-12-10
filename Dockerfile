FROM openjdk:8-jdk-alpine
WORKDIR /app/
RUN mkdir -p /app/
EXPOSE 8080

ADD build/libs/mra_tp*.jar /app/mra_tp.jar
ENTRYPOINT ["java","-jar","/app/mra_tp.jar"]