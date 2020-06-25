using System;
using System.Threading.Tasks;
using Amazon.DynamoDBv2;
using Amazon.DynamoDBv2.DocumentModel;

namespace AwsDotnetCsharp
{
    public class CatalogDAO
    {
        private readonly AmazonDynamoDBClient amazonDynamoDbClient;
        private readonly string TableName = "catalog";

        public CatalogDAO()
        {
            amazonDynamoDbClient = new AmazonDynamoDBClient();
        }

        public async Task<Document> getItem(string id) {
            var table = Table.LoadTable(amazonDynamoDbClient, TableName);
            var result = await table.GetItemAsync(id);

            if (result != null) {
                return result;
            }

            throw new Exception("There are no records with id " + id);
        }
    }
}
