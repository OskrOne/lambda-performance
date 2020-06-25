package com.serverless;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class CatalogDao {
	private final String tableName = "catalog";
	private AmazonDynamoDB client;

	public CatalogDao() {
		client = AmazonDynamoDBClientBuilder.defaultClient();
	}
	
	public Map<String, Object> getItem(String id) {
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tableName);
		Item item = table.getItem("id", id);
		
		if (item != null) {
			return item.asMap();
		}
		
		throw new RuntimeException("There are not record with id " + id);
	}
}
