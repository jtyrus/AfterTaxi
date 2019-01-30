package com.after.taxi.pricecheck.lyft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LyftClientImpl implements LyftClient{

	private RestTemplate restTemplate;
	
	@Value("${lyft.host}")
	private String lyftHost;
	
	@Value("${lyft.auth.path}")
	private String lyftAuthPath;
	
	@Value("${lyft.price.path}")
	private String lyftPricePath;
	
	private static final ObjectMapper objMapper = new ObjectMapper();
	
	public LyftClientImpl(@Autowired RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public LyftResponse getPrice(double startLat, double startLon, double endLat, double endLon) {
		return callLyft(startLat, startLon, endLat, endLon, getToken());
	}

	private LyftResponse callLyft(double startLat, double startLon, double endLat, double endLon, String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", accessToken);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(lyftHost)
				.path(lyftPricePath)
				.queryParam("start_lat", startLat)
				.queryParam("start_lng", startLon)
				.queryParam("end_lat", endLat)
				.queryParam("end_lng", endLon);
		
		ResponseEntity<LyftResponse> resp;
		try {
			resp = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity(headers), LyftResponse.class);
			return resp.getBody();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getToken() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic VWxUcjVOXy1xRTl1OjM2ZTU4bjR1Nkx3NFdsUm5UVEpKb1RnUjB2YW5SdExN");
		
		HttpEntity<String> entity = new HttpEntity<String>("{\"grant_type\": \"client_credentials\", \"scope\": \"public\"}", headers);
		
		JsonNode json;
		try {
			ResponseEntity<String> response = restTemplate.postForEntity( lyftHost + lyftAuthPath, entity , String.class );
			json = objMapper.readTree(response.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
		return json.get("token_type") + " " + json.get("access_token");
	}

}
