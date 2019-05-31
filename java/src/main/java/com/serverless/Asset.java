package com.serverless;

public class Asset {
	private String contractId;
	private String instrumentId;
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	
	@Override
	public String toString() {
		return "Asset [contractId=" + contractId + ", instrumentId=" + instrumentId + "]";
	}
}
