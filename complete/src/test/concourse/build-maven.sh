#!/bin/sh

docker daemon &
docker-compose up
mvnw install -Dverify.database=true
