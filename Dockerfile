FROM docker:latest

WORKDIR /app

# Install docker compose
RUN apk update && apk add --no-cache docker-compose bash

# Install java 21 corretto
RUN apk add --no-cache && \
    wget -O /etc/apk/keys/amazoncorretto.rsa.pub https://apk.corretto.aws/amazoncorretto.rsa.pub && \
    echo "https://apk.corretto.aws" >> /etc/apk/repositories && \
    apk update && \
    apk add amazon-corretto-21

# Copy all the project
COPY . /app

# Add permission to the scripts
RUN chmod +x ./mvnw
RUN chmod +x ./build.sh
RUN chmod +x ./run-prod.sh

# Build and run the project
ENTRYPOINT ["./run-prod.sh"]