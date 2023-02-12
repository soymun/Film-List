FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} film.jar
ENTRYPOINT ["java","-jar","/film.jar"]