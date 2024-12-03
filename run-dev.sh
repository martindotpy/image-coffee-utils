#!/bin/bash
PREFIX="image-coffee-utils-"

mkdir -p logs

# Run the config server
echo "Running the config server: ${PREFIX}config"
./mvnw -f ${PREFIX}config clean install spring-boot:run -q -DskipTests >logs/config.log 2>&1 &

sleep 10

# Run the eureka server
echo "Running the eureka server: ${PREFIX}eureka"
./mvnw -f ${PREFIX}eureka clean install spring-boot:run -q -DskipTests >logs/eureka.log 2>&1 &

sleep 5

# Run the gateway server
echo "Running the gateway server: ${PREFIX}gateway"
./mvnw -f ${PREFIX}gateway clean install spring-boot:run -q -DskipTests >logs/gateway.log 2>&1 &

sleep 10

# Run the ui service
echo "Running the ui service: ${PREFIX}ui"
./mvnw -f ${PREFIX}ui clean install spring-boot:run -q -DskipTests >logs/ui.log 2>&1 &

# Run the resize service
echo "Running the resize service: ${PREFIX}resize"
./mvnw -f ${PREFIX}resize clean install spring-boot:run -q -DskipTests >logs/ui.log 2>&1 &

# Run the colors service
echo "Running the colors service: ${PREFIX}colors"
cd ${PREFIX}colors && poetry install && poetry run python app.py >logs/colors.log 2>&1 &

cd ..

# Run the crop service
echo "Running the crop service: ${PREFIX}crop"
cd ${PREFIX}crop && dotnet watch run >logs/crop.log 2>&1 &
