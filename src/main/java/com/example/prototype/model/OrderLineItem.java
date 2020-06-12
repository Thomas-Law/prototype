package com.example.prototype.model;

public class OrderLineItem {
	
	private String fulfillmentState;
	private String paymentState;

	public String getFulfillmentState() {
		return fulfillmentState;
	}

	public void setFulfillmentState(String fulfillmentState) {
		this.fulfillmentState = fulfillmentState;
	}

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

}
