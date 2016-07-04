#!/bin/sh

docker daemon &
docker-compose up -d
./mvnw install -Dverify.database=true
