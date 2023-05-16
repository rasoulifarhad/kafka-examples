## Run Sample with diferent config

- ***One Partitions, One Consumers***

- ***One Partitions, Two Consumers***

- ***Two Partitions, Two Consumers***

---

***Start Kafka***

```sh
$ docker compose up -d
```

---

### One Partition, One Consumers


#### Producer:

```sh 
$ cd basic-producer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8080 --demo-topic.partitions=1"
```
   

#### Consumer:

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --demo-topic.partitions=1"
```

  
#### Test Sample

  
Here is an example of curl command posting a cloud event in binary-mode:

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

---

### One Partition, Two Consumers

1. ***Start Producer***

```sh 
$ cd basic-producer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8080 --demo-topic.partitions=1"
```
   
2. ***Start Consumer 1***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=1"
```

3. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 1 log:**

```
2023-05-16 17:12:32.649  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 1 , Value: 1 , Offset: 0
2023-05-16 17:12:32.649  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 2 , Value: 2 , Offset: 1
2023-05-16 17:12:32.649  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 3 , Value: 3 , Offset: 2
2023-05-16 17:12:32.649  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 4 , Value: 4 , Offset: 3

....

2023-05-16 17:12:32.656  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 94 , Value: 94 , Offset: 93
2023-05-16 17:12:32.656  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 95 , Value: 95 , Offset: 94
2023-05-16 17:12:32.656  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 96 , Value: 96 , Offset: 95
2023-05-16 17:12:32.656  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 97 , Value: 97 , Offset: 96
2023-05-16 17:12:32.656  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 98 , Value: 98 , Offset: 97
2023-05-16 17:12:32.656  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 99 , Value: 99 , Offset: 98
2023-05-16 17:12:32.656  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 100 , Value: 100 , Offset: 99
```

4. ***Start Consumer 2***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8082 --spring.application.name=consumer02 --demo-topic.partitions=1"
```

5. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 1 logs:**

```
2023-05-16 17:15:13.962  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 101 , Value: 101 , Offset: 100
2023-05-16 17:15:13.963  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 102 , Value: 102 , Offset: 101
2023-05-16 17:15:13.963  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 103 , Value: 103 , Offset: 102
2023-05-16 17:15:13.963  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 104 , Value: 104 , Offset: 103
2023-05-16 17:15:13.963  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 105 , Value: 105 , Offset: 104

...

2023-05-16 17:15:13.972  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 197 , Value: 197 , Offset: 196
2023-05-16 17:15:13.972  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 198 , Value: 198 , Offset: 197
2023-05-16 17:15:13.972  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 199 , Value: 199 , Offset: 198
2023-05-16 17:15:13.972  INFO 59527 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 200 , Value: 200 , Offset: 199
```

6. ***Stop Consumer 1***


7. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 2 logs:**

```
2023-05-16 17:17:33.742  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 201 , Value: 201 , Offset: 200
2023-05-16 17:17:33.742  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 202 , Value: 202 , Offset: 201
2023-05-16 17:17:33.742  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 203 , Value: 203 , Offset: 202
2023-05-16 17:17:33.742  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 204 , Value: 204 , Offset: 203
2023-05-16 17:17:33.742  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 205 , Value: 205 , Offset: 204
2023-05-16 17:17:33.742  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 206 , Value: 206 , Offset: 205
2023-05-16 17:17:33.742  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 207 , Value: 207 , Offset: 206

...

2023-05-16 17:17:33.746  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 295 , Value: 295 , Offset: 294
2023-05-16 17:17:33.746  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 296 , Value: 296 , Offset: 295
2023-05-16 17:17:33.746  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 297 , Value: 297 , Offset: 296
2023-05-16 17:17:33.746  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 298 , Value: 298 , Offset: 297
2023-05-16 17:17:33.746  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 299 , Value: 299 , Offset: 298
2023-05-16 17:17:33.747  INFO 60741 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer02 ,Key: 300 , Value: 300 , Offset: 299
```

***Note:***
> Now `Consumer 2` consuming the messages.

8. ***Start Consumer 1***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=1"
```

9. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

***Note:***
> Now `Consumer 1` consuming the messages.

**Consumer 1 logs:**
 
```
2023-05-16 17:20:31.550  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 301 , Value: 301 , Offset: 300
2023-05-16 17:20:31.551  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 302 , Value: 302 , Offset: 301
2023-05-16 17:20:31.551  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 303 , Value: 303 , Offset: 302
2023-05-16 17:20:31.551  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 304 , Value: 304 , Offset: 303
2023-05-16 17:20:31.552  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 305 , Value: 305 , Offset: 304

...

2023-05-16 17:20:31.566  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 397 , Value: 397 , Offset: 396
2023-05-16 17:20:31.566  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 398 , Value: 398 , Offset: 397
2023-05-16 17:20:31.566  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 399 , Value: 399 , Offset: 398
2023-05-16 17:20:31.566  INFO 62495 --- [           main] c.f.e.kafka.consumedemo.Application      : App: consumer01 ,Key: 400 , Value: 400 , Offset: 399
```

### Two Partitions, Two Consumers


1. ***Start Producer***

```sh 
$ cd basic-producer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8080 --demo-topic.partitions=2"
```
   
2. ***Start Consumer 1***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=2"
```

3. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 1 log:**

```

```

4. ***Start Consumer 2***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8082 --spring.application.name=consumer02 --demo-topic.partitions=2"
```

5. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 1 logs:**

```

```

6. ***Stop Consumer 1***


7. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 2 logs:**

```


```

***Note:***
> Now `Consumer 2` consuming the messages.

8. ***Start Consumer 1***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=2"
```

9. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

***Note:***
> Now `Consumer 1` consuming the messages.

**Consumer 1 logs:**
 
```

```

