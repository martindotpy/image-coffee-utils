#!/bin/bash

# Build the java images
./mvnw clean package spring-boot:build-image -DskipTests
