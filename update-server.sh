#!/bin/bash
ls -la
sudo su
echo "---shutting down all running containers---"
for container_id in $(docker ps -q); do
  echo "$container_id"
  docker kill "$container_id"
done
echo "--------rebuild---------"
docker-compose -f docker-compose.yml build --no-cache
echo "--------restart---------"
docker-compose -f docker-compose.yml up -d
exit
exit
