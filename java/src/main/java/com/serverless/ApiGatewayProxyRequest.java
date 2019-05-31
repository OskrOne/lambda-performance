package com.serverless;

public class ApiGatewayProxyRequest {
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ApiGatewayProxyRequest [body=" + body + "]";
	}
}
