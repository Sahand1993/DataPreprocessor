#!/bin/bash

docker stop datasets-mysql

docker rm datasets-mysql

docker rmi datasets-mysql

docker build -t datasets-mysql --no-cache .

docker run -d -p 3306:3306 --name datasets-mysql -e MYSQL_ROOT_PASSWORD=$DATASET_MYSQL_PASSWORD datasets-mysql

docker start datasets-mysql 
