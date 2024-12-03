#!/bin/bash
# Remove containers
sudo docker ps -a --filter "name=image-coffee-utils.*" -q | xargs -r sudo docker rm -f

# Remove images
sudo docker images --format "{{.Repository}}:{{.Tag}}" | grep "image-coffee-utils" | xargs -r sudo docker rmi -f

# List volumes and filter by name (if applicable)
sudo docker volume ls | grep "image-coffee-utils" | awk '{print $2}' | xargs -r sudo docker volume rm
