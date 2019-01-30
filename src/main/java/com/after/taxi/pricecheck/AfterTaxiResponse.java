package com.after.taxi.pricecheck;

class AfterTaxiResponse {	
	private String price;
	private String type;
	
	public AfterTaxiResponse(String price, String type) {
		this.price = price;
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}