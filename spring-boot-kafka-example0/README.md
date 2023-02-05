## Run Sample
#
# Run docker
#
#  docker compose up -d
#
# Run producer app - producer-kafka
#
#  ./mvnw spring-boot:run
#
# Run consumer app - consumer-kafka
#
#  ./mvnw spring-boot:run
#  
## Test 
#
#   curl -w'\n' localhost:8090/api/alerts -H "Content-Type: application/json"  -d '{"level":"1","message":"be careful!"}' 
#
