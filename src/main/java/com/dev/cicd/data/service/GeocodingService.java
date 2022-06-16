package com.dev.cicd.data.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dev.cicd.data.dto.AddressResult;
import com.dev.cicd.data.dto.GeocodingRequestDto;
import com.dev.cicd.data.dto.GeocodingResponseDto;

@Service
public class GeocodingService implements IGeocodingService {

	@Override
	public GeocodingResponseDto geocode(GeocodingRequestDto geocodingRequestDto) {
		
		AddressResult[] results = WebClient.builder()
				.baseUrl("https://nominatim.openstreetmap.org")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build().get()
				.uri(uriBuilder -> uriBuilder
						.path("/")
						.queryParam("addressdetails", "2")
						.queryParam("q", geocodingRequestDto.getCity()+","+geocodingRequestDto.getCountry())
						.queryParam("format", "json")
						.queryParam("limit", "1")
						.queryParam("accept-language", "en")
						.build())
				.retrieve()
				.bodyToMono(AddressResult[].class).share()
				.block();

		AddressResult result = results[0];
		
		GeocodingResponseDto geocodingResponseDto = new GeocodingResponseDto();
		
		if(result.getAddress().getCity()!=null) {
			geocodingResponseDto.setCity(result.getAddress().getCity());
		}else if(result.getAddress().getVillage()!=null) {
			geocodingResponseDto.setCity(result.getAddress().getVillage());
		}
		

		geocodingResponseDto.setCountry(result.getAddress().getCountry());
		geocodingResponseDto.setState(result.getAddress().getState());
		geocodingResponseDto.setCounty(result.getAddress().getCounty());
		geocodingResponseDto.setPostcode(result.getAddress().getPostcode());
		geocodingResponseDto.setLatitude(result.getLat());
		geocodingResponseDto.setLongitude(result.getLon());

		
		// TODO Auto-generated method stub
		return geocodingResponseDto;
	}

}
