# Notifications API


### Available endpoints
- **POST** `/notifications` In order to create a new notification
- **GET** `/notifications/by_user/{user_id}` In order to retrieve the last (20 by default) user notifications ordered in descending order
  - Note: `count` query parameter is accepted to specify the amount of notifications to be retrieved
- **PUT** `/notifications/update/{id}` In order to update a notification

### Build
```
./gradlew build installDist
docker build -t notifications-api .
```

### Running the built docker image
Basic execution:
```
docker run -p 8080:8080 notifications-api
```