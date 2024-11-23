#!/bin/bash
PREFIX="image-coffee-utils-"

# Define the java microservices
elements=("config" "eureka" "gateway" "ui")

for e in "${elements[@]}"; do
    ./mvnw.sh -f "$PREFIX$e" clean install spring-boot:build-image -DskipTests &
done

wait
