#!/bin/bash
PREFIX="image-coffee-utils-"

mkdir -p logs

# Run the config server
echo "Running the config server: ${PREFIX}config"
./mvnw.sh -f ${PREFIX}config/pom.xml clean install spring-boot:run -q -DskipTests >logs/config.log 2>&1 &

sleep 10

# Run the eureka server
echo "Running the eureka server: ${PREFIX}eureka"
./mvnw.sh -f ${PREFIX}eureka/pom.xml clean install spring-boot:run -q -DskipTests >logs/eureka.log 2>&1 &

sleep 5

# Run the gateway server
echo "Running the gateway server: ${PREFIX}gateway"
./mvnw.sh -f ${PREFIX}gateway/pom.xml clean install spring-boot:run -q -DskipTests >logs/gateway.log 2>&1 &

sleep 10

# Run the ui service
echo "Running the ui service: ${PREFIX}ui"
./mvnw.sh -f ${PREFIX}ui/pom.xml clean install spring-boot:run -q -DskipTests >logs/ui.log 2>&1 &

# Run the colors service
echo "Running the colors service: ${PREFIX}colors"
cd ${PREFIX}colors && poetry install && poetry run python app.py >logs/colors.log 2>&1 &
