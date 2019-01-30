package com.after.taxi.pricecheck.geocode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

@Service
public class GeocodeServiceImpl implements GeocodeService {

	@Value("${google.api.key}")
	private String apiKey;
	
	@Override
	public GeoCoordinates getCoordinates(String address) throws Exception {
		GeoApiContext context = new GeoApiContext.Builder()
			    .apiKey(apiKey)
			    .build();
		GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
		
		double lat = results[0].geometry.location.lat;
		double lng = results[0].geometry.location.lng;
		return new GeoCoordinates(lat, lng);
	}
}
