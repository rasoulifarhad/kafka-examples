## Run Sample with different config

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
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8080 --demo-topic.partitions=1"
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
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8080 --demo-topic.partitions=1"
```
   
2. ***Start Consumer 1***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run \
    -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=1"
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
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8082 --spring.application.name=consumer02 --demo-topic.partitions=1"
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
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=1"
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
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8080 --demo-topic.partitions=2"
```
   
2. ***Start Consumer 1***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=2"
```

3. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 1 log:**

```
2023-05-16 19:05:52.143  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  1,  1] FROM  [P1 ,O0  ]
2023-05-16 19:05:52.144  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  3,  3] FROM  [P1 ,O1  ]
2023-05-16 19:05:52.144  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  4,  4] FROM  [P1 ,O2  ]
2023-05-16 19:05:52.144  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  7,  7] FROM  [P1 ,O3  ]
2023-05-16 19:05:52.144  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  8,  8] FROM  [P1 ,O4  ]

...

2023-05-16 19:05:52.148  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [ 99, 99] FROM  [P1 ,O48 ]
2023-05-16 19:05:52.148  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [100,100] FROM  [P1 ,O49 ]
2023-05-16 19:05:52.148  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  2,  2] FROM  [P0 ,O0  ]
2023-05-16 19:05:52.148  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  5,  5] FROM  [P0 ,O1  ]
2023-05-16 19:05:52.148  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [  6,  6] FROM  [P0 ,O2  ]

...

2023-05-16 19:05:52.152  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [ 94, 94] FROM  [P0 ,O47 ]
2023-05-16 19:05:52.152  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [ 95, 95] FROM  [P0 ,O48 ]
2023-05-16 19:05:52.152  INFO 108156 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [ 98, 98] FROM  [P0 ,O49 ]
```

***Note:***
> Now `Consumer 1` assigned to P0 and P1


4. ***Start Consumer 2***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8082 --spring.application.name=consumer02 --demo-topic.partitions=2"
```

5. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 1 logs:**

```
2023-05-16 19:08:55.654  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [102,102] FROM  [P1 ,O50 ]
2023-05-16 19:08:55.654  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [104,104] FROM  [P1 ,O51 ]
2023-05-16 19:08:55.654  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [105,105] FROM  [P1 ,O52 ]
2023-05-16 19:08:55.655  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [107,107] FROM  [P1 ,O53 ]

...

2023-05-16 19:08:55.663  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [191,191] FROM  [P1 ,O92 ]
2023-05-16 19:08:55.665  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [193,193] FROM  [P1 ,O93 ]
2023-05-16 19:08:55.665  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [194,194] FROM  [P1 ,O94 ]
2023-05-16 19:08:55.665  INFO 109058 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [197,197] FROM  [P1 ,O95 ]
```

***Note:***
> Now `Consumer 1` assigned to P1


**Consumer 2 logs:**

```
2023-05-16 19:08:55.655  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [101,101] FROM  [P0 ,O50 ]
2023-05-16 19:08:55.656  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [103,103] FROM  [P0 ,O51 ]
2023-05-16 19:08:55.657  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [106,106] FROM  [P0 ,O52 ]
2023-05-16 19:08:55.657  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [110,110] FROM  [P0 ,O53 ]

...

2023-05-16 19:08:55.666  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [196,196] FROM  [P0 ,O100]
2023-05-16 19:08:55.666  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [198,198] FROM  [P0 ,O101]
2023-05-16 19:08:55.666  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [199,199] FROM  [P0 ,O102]
2023-05-16 19:08:55.666  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [200,200] FROM  [P0 ,O103]
```

***Note:***
> and `Consumer 2` assigned to P0

6. ***Stop Consumer 1***


7. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

**Consumer 2 logs:**

```
2023-05-16 19:11:38.138  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [201,201] FROM  [P0 ,O104]
2023-05-16 19:11:38.139  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [202,202] FROM  [P0 ,O105]
2023-05-16 19:11:38.139  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [203,203] FROM  [P0 ,O106]

....

2023-05-16 19:11:38.146  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [298,298] FROM  [P0 ,O154]
2023-05-16 19:11:38.146  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [300,300] FROM  [P0 ,O155]


2023-05-16 19:12:11.023  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Request joining group due to: group is already rebalancing
2023-05-16 19:12:11.027  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Revoke previously assigned partitions demo-topic-0
2023-05-16 19:12:11.028  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] (Re-)joining group
2023-05-16 19:12:11.032  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Successfully joined group with generation Generation{generationId=4, memberId='consumer-my-group-1-1a05fcf9-259f-4dcc-ae34-0669d44a03d3', protocol='range'}
2023-05-16 19:12:11.035  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Finished assignment for group at generation 4: {consumer-my-group-1-1a05fcf9-259f-4dcc-ae34-0669d44a03d3=Assignment(partitions=[demo-topic-0, demo-topic-1])}
2023-05-16 19:12:11.039  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Successfully synced group in generation Generation{generationId=4, memberId='consumer-my-group-1-1a05fcf9-259f-4dcc-ae34-0669d44a03d3', protocol='range'}
2023-05-16 19:12:11.039  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Notifying assignor about the new Assignment(partitions=[demo-topic-0, demo-topic-1])
2023-05-16 19:12:11.039  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Adding newly assigned partitions: demo-topic-1, demo-topic-0
2023-05-16 19:12:11.041  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Setting offset for partition demo-topic-1 to the committed offset FetchPosition{offset=96, offsetEpoch=Optional[0], currentLeader=LeaderAndEpoch{leader=Optional[localhost:9092 (id: 1 rack: null)], epoch=0}}
2023-05-16 19:12:11.041  INFO 109438 --- [           main] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-my-group-1, groupId=my-group] Setting offset for partition demo-topic-0 to the committed offset FetchPosition{offset=156, offsetEpoch=Optional[0], currentLeader=LeaderAndEpoch{leader=Optional[localhost:9092 (id: 1 rack: null)], epoch=0}}


2023-05-16 19:12:11.254  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [206,206] FROM  [P1 ,O96 ]
2023-05-16 19:12:11.254  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [208,208] FROM  [P1 ,O97 ]
2023-05-16 19:12:11.254  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [209,209] FROM  [P1 ,O98 ]

...

2023-05-16 19:12:11.258  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [292,292] FROM  [P1 ,O141]
2023-05-16 19:12:11.258  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [296,296] FROM  [P1 ,O142]
2023-05-16 19:12:11.258  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [299,299] FROM  [P1 ,O143]
```

***Note:***
> Now `Consumer 2` assigned to P0 and P1

8. ***Start Consumer 1***

```sh 
$ cd basic-consumer-demo
$ ./mvnw spring-boot:run \
   -Dspring-boot.run.arguments="--server.port=8081 --spring.application.name=consumer01 --demo-topic.partitions=2"
```

9. ***Send messages***

```sh
$ curl -w'\n' -s localhost:8080/sendMessages -H "Content-Type: application/json" 
```

***Note:***
> Now `Consumer 1` consuming the messages from P1.

**Consumer 1 logs:**
 
```
2023-05-16 19:18:38.870  INFO 112749 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [301,301] FROM  [P1 ,O144]
2023-05-16 19:18:38.870  INFO 112749 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [302,302] FROM  [P1 ,O145]
2023-05-16 19:18:38.870  INFO 112749 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [306,306] FROM  [P1 ,O146]
2023-05-16 19:18:38.870  INFO 112749 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [307,307] FROM  [P1 ,O147]

...

2023-05-16 19:18:38.873  INFO 112749 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [398,398] FROM  [P1 ,O192]
2023-05-16 19:18:38.873  INFO 112749 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer01 CONSUMES [400,400] FROM  [P1 ,O193]

```

**Consumer 1 logs:**
 
```
2023-05-16 19:18:38.862  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [303,303] FROM  [P0 ,O156]
2023-05-16 19:18:38.862  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [304,304] FROM  [P0 ,O157]
2023-05-16 19:18:38.862  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [305,305] FROM  [P0 ,O158]

...

2023-05-16 19:18:38.864  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [396,396] FROM  [P0 ,O203]
2023-05-16 19:18:38.864  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [397,397] FROM  [P0 ,O204]
2023-05-16 19:18:38.864  INFO 109438 --- [           main] c.f.e.kafka.consumedemo.Application      : consumer02 CONSUMES [399,399] FROM  [P0 ,O205]
```

***Note:***
> and `Consumer 2` consuming the messages from P0.

