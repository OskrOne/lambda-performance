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

type Response events.APIGatewayProxyResponse

// Handler is our lambda handler invoked by the `lambda.Start` function call
func Handler(ctx context.Context) (Response, error) {

	configuration, err := external.LoadDefaultAWSConfig()
	if err != nil {
		return Response{StatusCode: 500}, err
	}

	svc := dynamodb.New(configuration)
	request := svc.GetItemRequest(&dynamodb.GetItemInput{
			TableName: aws.String("catalog"),
			Key: map[string]dynamodb.AttributeValue{
				"id": dynamodb.AttributeValue {
					S: aws.String("test"),
				},
			},
	})
	response, err2 := request.Send(context.TODO())

	if err2 != nil {
		return Response{StatusCode: 500}, err2
	}

	type Catalog struct {
		Id string
		Value string
	}
	
	catalogResult := Catalog {
		Id: *response.Item["id"].S,
		Value: *response.Item["value"].S,
	}

	jsonData, err3 := json.Marshal(catalogResult)

	if err3 != nil {
		return Response{StatusCode: 404}, err3
	}

	resp := Response{
		StatusCode:      200,
		IsBase64Encoded: false,
		Body:            string(jsonData),
	}

	return resp, nil
}

func main() {
	lambda.Start(Handler)
}