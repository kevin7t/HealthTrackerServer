#!/bin/bash

sudo ssh -N ${1} \
 -L 80:localhost:80 \
 -L 8081:localhost:8081 \
 -L 8082:localhost:8082 \
 -L 8083:localhost:8083 \
 -L 8084:localhost:8084 \
 -L 8085:localhost:8085 \
 -L 8086:localhost:8086 \
 -L 8087:localhost:8087 \
 -L 8088:localhost:8088 \
 -L 8089:localhost:8089 \
 -L 9000:localhost:9000 \
 -L 9081:localhost:9081 \
 -L 9088:localhost:9088 \
 -L 8285:localhost:8285 \
 -L 9100:localhost:9100 \
 -L 8288:localhost:8288 \
 -L 15672:localhost:15672 \
 -L 8091:localhost:8091 \
 -L 5672:localhost:5672

 #For use if the docker is deployed elsewhere and laptop is ssh'd into it
