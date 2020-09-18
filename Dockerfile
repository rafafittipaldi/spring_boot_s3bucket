FROM openjdk:8

ADD target/*jar app.jar

CMD ["java", "-jar", "app.jar"]