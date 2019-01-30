package com.after.taxi.pricecheck;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public interface RestService {
	
	@GetMapping("/checkPrice")
	@ApiOperation(value = "CheckPrice")
	@ResponseBody
	public List<AfterTaxiResponse> CheckPrice(
		@RequestParam("startLat") Double startLat,
		@RequestParam("startLng") Double startLon,
		@RequestParam("endLat") Double endLat, 
		@RequestParam("endLng") Double endLng,
		@ApiParam(required = false, value = "Friendly address if lat/long not provided") @RequestParam("startAddress") String startAddress,
		@ApiParam(required = false, value = "Friendly address if lat/long not provided") @RequestParam("endAddress") String endAddress
	);
	
}
