## Run Sample : 

```sh
$ docker compose up -d
```

```sh 
$ ./mvnw spring-boot:run
```
     
## Test Sample

  
Here is an example of curl command posting a cloud event in binary-mode:

```sh
$ curl -w'\n' localhost:8080/api/sum -H "Content-Type: application/json"  -d '{"firstNumber":"10","secondNumber":"20"}' -
```

