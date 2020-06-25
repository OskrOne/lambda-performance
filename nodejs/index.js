'use strict';
var AWS = require('aws-sdk');

module.exports.handler = async (event) => {
  const docClient = new AWS.DynamoDB.DocumentClient();
  const params = {
    TableName: 'catalog',
    Key: {
      'id': 'test'
    }
  }
  const data = await docClient.get(params).promise();
  console.log(data);
  
  return {
    statusCode: 200,
    body: JSON.stringify(data.Item)
  };
};
