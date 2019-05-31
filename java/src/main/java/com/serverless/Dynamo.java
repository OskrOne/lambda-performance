package com.serverless;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class Dynamo {
	
	private final String tableName = "Assets";
	
	public Asset getItem(String contractId)  {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDB dynamoDB = new DynamoDB(client);
		
		Table table = dynamoDB.getTable(tableName);
		Item item = table.getItem("ContractId", contractId);
		
		
		if (item != null) {
			Asset asset = new Asset();
			asset.setContractId(item.getString("ContractId"));
			asset.setInstrumentId(item.getString("InstrumentId"));
			return asset;
		}
		
		throw new RuntimeException("There are no assets with id " + contractId);
	}
}
