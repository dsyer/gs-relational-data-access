#!/bin/sh

service docker start
docker-compose up
./mvnw install -Dverify.database=true
