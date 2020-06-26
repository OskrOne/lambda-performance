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