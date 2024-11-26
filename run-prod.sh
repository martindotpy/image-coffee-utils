#!/bin/bash

# Build the java images
./build.sh

# Run the docker-compose
docker compose up -d
