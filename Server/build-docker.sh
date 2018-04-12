#! /bin/bash -e

./gradlew assemble
docker build -t healthtrackerserver .
