FROM java:openjdk-8u91-jdk
EXPOSE 8080
ADD build/libs/Server.jar Server.jar
ADD src/main/resources/application.properties application.properties
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/application.properties","-jar","Server.jar"]


