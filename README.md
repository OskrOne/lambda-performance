# lambda-performance
Project to test the performance with AWS Lambda using different languages: C#, Java, Python, NodeJS &amp; GO

## Instructions

sls deploy
aws dynamodb put-item --table-name catalog --item '{"id": { "S": "test"}, "value": { "S": "something"}}'

### Java
mvn clean package