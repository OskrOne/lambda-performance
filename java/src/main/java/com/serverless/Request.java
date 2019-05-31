package com.serverless;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
	
	@JsonProperty("ContractId")
	private String contractId;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Override
	public String toString() {
		return "Request [contractId=" + contractId + "]";
	}
}
