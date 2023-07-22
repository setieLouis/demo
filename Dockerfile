FROM openjdk:17
COPY ./build/libs/demo.jar app.jar
EXPOSE  9000/tcp
ENTRYPOINT ["java","-jar","/app.jar"]