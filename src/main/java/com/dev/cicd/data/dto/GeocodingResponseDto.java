package com.dev.cicd.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodingResponseDto {
	
	private String city;
	private String country;
	private String state;
	private String county;
	private String postcode;
	private Double longitude;
	private Double latitude;
	
}
