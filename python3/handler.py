from __future__ import print_function
import json
import boto3
import decimal

# This is because for some reason, json dumps does not support decimal 
class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            if o % 1 > 0:
                return float(o)
            else:
                return int(o)
        return super(DecimalEncoder, self).default(o)

def hello(event, context):
    dynamodb = boto3.resource('dynamodb')
    table = dynamodb.Table('Assets')
    body = json.loads(event['body'])

    res = table.get_item(
        Key={
            'ContractId': body['ContractId']
        }
    )

    item = res['Item']

    response = {
        "statusCode": 200,
        "body": json.dumps(item, cls=DecimalEncoder)
    }

    return response
