## Run Sample
#
# Run docker
#
#  docker compose up -d
#
# Run producer app - producer-cloud-stream
#
#  ./mvnw spring-boot:run
#
# Run consumer app - consumer-cloud-stream
#
#  ./mvnw spring-boot:run
#  
## Test 
#
#   curl -w'\n' localhost:8080/api/alerts -H "Content-Type: application/json"  -d '{"level":"1","message":"be careful!"}' -
#
