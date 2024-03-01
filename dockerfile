FROM openjdk:17

ADD target/book-system.jar book-system.jar

EXPOSE 8080

CMD ["java", "-jar", "book-system.jar"]