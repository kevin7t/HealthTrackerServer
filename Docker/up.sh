#! /bin/bash
cd Server && docker build -t healthtrackerserver . 

docker-compose up -d
