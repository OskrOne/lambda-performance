package com.serverless;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);
		CatalogDao dao = new CatalogDao();
		
		Map<String, Object> item = dao.getItem("test");
				
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(item)
				.build();
	}
}
