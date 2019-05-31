package com.serverless;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Handler implements RequestHandler<ApiGatewayProxyRequest, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(ApiGatewayProxyRequest input, Context context) {
		LOG.info("received: {}", input);
		Dynamo dynamo = new Dynamo();
		Request request = getRequest(input);
		Asset item = dynamo.getItem(request.getContractId());
		
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(item)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}

	private Request getRequest(ApiGatewayProxyRequest input) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Request result =  mapper.readValue(input.getBody(), Request.class);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("There was an error with serialization");
		}
	}
}
