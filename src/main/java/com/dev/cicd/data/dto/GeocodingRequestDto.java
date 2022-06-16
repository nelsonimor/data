package com.dev.cicd.data.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodingRequestDto {
	
	@NotNull
	private String city;
	
	@NotNull
	private String country;
	

}
