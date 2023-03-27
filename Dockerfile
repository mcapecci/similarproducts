FROM amazoncorretto:11-alpine-jdk
COPY ./target/similarproducts-0.0.1-SNAPSHOT.jar similarproducts-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","similarproducts-0.0.1-SNAPSHOT.jar"]
EXPOSE 5000