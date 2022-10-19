FROM openjdk:13.0.2

WORKDIR /notifications-api/bin

ENV LOG_PATH /data/logs

EXPOSE 8080

# Copy the application folder
COPY ./build/install/notifications-api/ /notifications-api

ENTRYPOINT ["./notifications-api"]
