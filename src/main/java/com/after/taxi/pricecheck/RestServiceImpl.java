package com.after.taxi.pricecheck;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.InternalServerErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.after.taxi.pricecheck.geocode.GeoCoordinates;
import com.after.taxi.pricecheck.geocode.GeocodeService;
import com.after.taxi.pricecheck.lyft.LyftClient;
import com.after.taxi.pricecheck.lyft.LyftResponse;

@Controller
public class RestServiceImpl implements RestService {

	private LyftClient lyftClient;
	private GeocodeService geocodeService;
	
	@Value("${discount.rate}")
	private double discountRate;
	
	private static final DecimalFormat formatter = new DecimalFormat("$#0.00");
	
	@Autowired
	public RestServiceImpl(LyftClient lyftClient, GeocodeService geocodeService) {
		this.lyftClient = lyftClient;
		this.geocodeService = geocodeService;
	}
	
	@Override
	public List<AfterTaxiResponse> CheckPrice(Double startLat, Double startLng, Double endLat, Double endLng, String startAddress,
			String endAddress) {
		try {
			if (startAddress != null && !startAddress.isEmpty() && endAddress != null && !endAddress.isEmpty()) {
				GeoCoordinates start = geocodeService.getCoordinates(startAddress);
				GeoCoordinates end = geocodeService.getCoordinates(endAddress);
				startLat = start.latitude;
				startLng = start.longitude;
				endLat = end.latitude;
				endLng = end.longitude;
			}
			return mapLyftResponse(lyftClient.getPrice(startLat, startLng, endLat, endLng));
		}
		catch (Exception ex) {
			throw new InternalServerErrorException();
		}
	}

	private List<AfterTaxiResponse> mapLyftResponse(LyftResponse lyftResp) {
		return lyftResp.getCostEstimates()
			.stream()
			.map(lyftCost -> 
				new AfterTaxiResponse(formatter.format((lyftCost.getCost() * discountRate)), getRideType(lyftCost.getRideType())))
			.collect(Collectors.toList());
	}
	
	private String getRideType(String lyftRideType) {
		StringBuilder afterType = new StringBuilder("After");
		
		Arrays.stream(lyftRideType.split("_"))
			.map(input -> input.substring(0, 1).toUpperCase() + input.substring(1))
			.forEach(capitalized -> afterType.append(' ').append(capitalized));
		return afterType.toString();
	}
}
