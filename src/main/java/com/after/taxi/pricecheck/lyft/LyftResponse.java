package com.after.taxi.pricecheck.lyft;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LyftResponse {
	
	@JsonProperty("cost_estimates")
	private List<CostEstimate> costEstimates;
	
	public List<CostEstimate> getCostEstimates() {
		return costEstimates;
	}

	public void setCostEstimates(List<CostEstimate> costEstimates) {
		this.costEstimates = costEstimates;
	}
}
