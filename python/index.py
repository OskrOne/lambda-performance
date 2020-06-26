import json
import boto3

def handler(event, context):
    response = {
        "statusCode": 200,
        "body": json.dumps(getItem())
    }

    return response

def getItem():
    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('catalog')
    res = table.get_item(
        Key={
            'id': 'test'
        }
    )

    item = res['Item']

    return item