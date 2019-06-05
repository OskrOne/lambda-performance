using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Amazon.DynamoDBv2;
using Amazon.DynamoDBv2.DocumentModel;
using Amazon.DynamoDBv2.Model;

namespace AwsDotnetCsharp
{
    class Dynamo
    {
        private readonly AmazonDynamoDBClient amazonDynamoDbClient;
        private readonly string AssetTableName = "Assets";

        public Dynamo() {
            amazonDynamoDbClient = new AmazonDynamoDBClient();
        }
        /// <summary>
        /// Get Item using Document Level
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public async Task<Document> getItem(string contractId) {

            insertItemsMassivelyAsync();

            var table = Table.LoadTable(amazonDynamoDbClient, AssetTableName);
            var result = await table.GetItemAsync(contractId);

            if (result == null) {
                throw new Exception("There are no assets with id " + contractId);
            }

            return result;
        }
        /// <summary>
        /// Insert 10,000 items in DynamoDB
        /// </summary>
        public async void insertItemsMassivelyAsync()
        {
            Random random = new Random();
            var table = Table.LoadTable(amazonDynamoDbClient, AssetTableName);
            
            for (int index = 1; index < 1000; index++) {
                var asset = new Document();
                asset["ContractId"] = "A" + index;
                asset["InstrumentId"] = random.Next(100000, 999999);
                var result = await table.PutItemAsync(asset);
            }
        }

        /// <summary>
        /// Get Item using Dynamo Service Level API
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        private async Task<Dictionary<string, AttributeValue>> getItemUsingServices(string contractId)
        {
            Dictionary<string, AttributeValue> key = new Dictionary<string, AttributeValue> {
                {
                    "ContractId", new AttributeValue{ S = contractId }
                }
            };

            GetItemRequest getItemRequest = new GetItemRequest
            {
                TableName = AssetTableName,
                Key = key
            };

            var result = await amazonDynamoDbClient.GetItemAsync(getItemRequest);
            if (result == null)
            {
                throw new Exception("There are no assets with id " + contractId);
            }

            return result.Item;
        }
    }
}
