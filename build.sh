#!/bin/bash

# Define the java microservices
elements=("image-coffee-utils-config" "image-coffee-utils-eureka" "image-coffee-utils-gateway" "image-coffee-utils-ui" "image-coffee-utils-resize")

./mvnw clean install package -DskipTests

for e in "${elements[@]}"; do
    ./mvnw -f "$e" spring-boot:build-image -DskipTests &
done

wait
