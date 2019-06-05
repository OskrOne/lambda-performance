using Amazon.DynamoDBv2.DocumentModel;
using Amazon.Lambda.Core;
using System.Collections.Generic;
using Newtonsoft.Json;
using Amazon.Lambda.APIGatewayEvents;
using System.Net;

[assembly: LambdaSerializer(typeof(Amazon.Lambda.Serialization.Json.JsonSerializer))]

namespace AwsDotnetCsharp
{
    public class Handler
    {
        public APIGatewayProxyResponse Hello(APIGatewayProxyRequest request)
        {
            Request finalRequest = JsonConvert.DeserializeObject<Request>(request.Body);
            Dynamo dynamo = new Dynamo();

            Document item = dynamo.getItem(finalRequest.ContractId).Result;
            return new APIGatewayProxyResponse
            {
                StatusCode = (int)HttpStatusCode.OK,
                Body = JsonConvert.SerializeObject(item),
                Headers = new Dictionary<string, string> { { "Content-Type", "application/json" } }
            };
        }
    }

    public class Request
    {
        public string ContractId { get; set; }

        public Request(string contractId)
        {
            ContractId = contractId;
        }

        public override string ToString()
        {
            return "ContractId: " + ContractId;
        }
    }
}
