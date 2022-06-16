package com.dev.cicd.data.service;

import com.dev.cicd.data.dto.GeocodingRequestDto;
import com.dev.cicd.data.dto.GeocodingResponseDto;

public interface IGeocodingService {
	
	GeocodingResponseDto geocode(GeocodingRequestDto geocodingRequestDto);

}
