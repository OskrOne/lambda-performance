package main

import (
	"context"
	"encoding/json"

	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"

	"github.com/aws/aws-sdk-go-v2/aws"
	"github.com/aws/aws-sdk-go-v2/aws/external"
	"github.com/aws/aws-sdk-go-v2/service/dynamodb"
)

// Response is of type APIGatewayProxyResponse since we're leveraging the
// AWS Lambda Proxy Request functionality (default behavior)
//
// https://serverless.com/framework/docs/providers/aws/events/apigateway/#lambda-proxy-integration
type Response events.APIGatewayProxyResponse
type Request events.APIGatewayProxyRequest

type Asset struct {
	ContractId string
	InstrumentId string
}

type Payload struct {
	ContractId string
}

// Handler is our lambda handler invoked by the `lambda.Start` function call
func Handler(ctx context.Context, request Request) (Response, error) {
	
	var payload Payload;
	err := json.Unmarshal([]byte(request.Body), &payload)
	
	if (err != nil) {
		return Response{
			StatusCode: 400,
			Body: "Invalid payload",
		}, err
	}
	
	item, err := getItem(payload)
	if (err != nil) {
		return Response{StatusCode: 404}, err
	}
	
	body, err := json.Marshal(item)
	
	if err != nil {
		return Response{StatusCode: 404}, err
	}

	resp := Response{
		StatusCode:      200,
		IsBase64Encoded: false,
		Body:            string(body),
		Headers: map[string]string{
			"Content-Type":           "application/json",
			"X-MyCompany-Func-Reply": "hello-handler",
		},
	}

	return resp, nil
}

func main() {
	lambda.Start(Handler)
}

func getItem(payload Payload) (*Asset, error) {
	asset := Asset{}
	configuration, err := external.LoadDefaultAWSConfig()
	
	if err != nil {
		return &asset, err
	}
	
	svc := dynamodb.New(configuration)
	request := svc.GetItemRequest(&dynamodb.GetItemInput{
			TableName: aws.String("Assets"),
			Key: map[string]dynamodb.AttributeValue{
				"ContractId": dynamodb.AttributeValue {
					S: aws.String(payload.ContractId),
				},
			},
	})
	
	response, err := request.Send(context.TODO())
	
	if (err != nil) {
		return &asset, err
	}
	
	assetResult := Asset {
		ContractId: *response.Item["ContractId"].S,
		InstrumentId: *response.Item["InstrumentId"].N,
	}	
		
	return &assetResult, nil;
}

