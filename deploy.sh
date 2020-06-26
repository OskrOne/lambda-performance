#!/bin/bash

cd csharp
./build.sh
cd ..
cd go
make deploy
cd ..
cd java
mvn clean package
cd ..
cd nodejs
npm install
cd ..
sls deploy
aws dynamodb put-item --table-name catalog --item '{"id": { "S": "test"}, "value": { "S": "something"}}'