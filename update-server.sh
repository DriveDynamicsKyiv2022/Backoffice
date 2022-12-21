#!/bin/bash
ls -la
sudo su
for container_id in $(docker ps -q); do
  echo "$container_id"
  docker kill "$container_id"
done
docker-compose -f docker-compose.yml build --no-cache
docker-compose -f docker-compose.yml up -d
exit
exit
