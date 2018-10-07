#! /bin/bash
./gradlew bootJar
cp -fr Server/build/libs/Server-1.0.jar Docker/Server/Server.jar
cd Docker/
./up.sh