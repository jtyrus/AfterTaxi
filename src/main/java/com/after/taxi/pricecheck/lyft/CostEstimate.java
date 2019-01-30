package com.after.taxi.pricecheck.lyft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CostEstimate {
	@JsonProperty("ride_type")
	public String rideType;
	@JsonProperty("estimated_cost_cents_max")
	public double cost;
	@JsonProperty("estimated_distance_miles")
	public double distance;
	
	public String getRideType() {
		return rideType;
	}
	public void setRideType(String rideType) {
		this.rideType = rideType;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}