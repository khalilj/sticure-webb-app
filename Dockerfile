FROM frolvlad/alpine-oraclejdk8:slim
ADD target/10bis-notification-0.0.1-SNAPSHOT.jar 10bis-notification.jar
EXPOSE 8080
RUN mkdir db-files
ENTRYPOINT [ "sh", "-c", "java -jar /10bis-notification.jar" ]
CMD
