'use strict';
var AWS = require("aws-sdk");
AWS.config.update({ region: 'us-east-2' });

module.exports.hello = async (event) => {
  var body = JSON.parse(event.body);
  var docClient = new AWS.DynamoDB.DocumentClient();
  var params = {
    TableName: "Assets",
    Key: {
      "ContractId": body.ContractId
    }
  }
  var data = await docClient.get(params).promise();
  
  return {
    statusCode: 200,
    body: JSON.stringify(data.Item, null, 2)
  };
};
