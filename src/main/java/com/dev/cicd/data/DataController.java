package com.dev.cicd.data;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.cicd.data.bo.CityBo;
import com.dev.cicd.data.dto.GeocodingRequestDto;
import com.dev.cicd.data.dto.GeocodingResponseDto;
import com.dev.cicd.data.service.ICityService;
import com.dev.cicd.data.service.IGeocodingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class DataController {

	@Autowired
	private ICityService cityService;
	
	@Autowired
	private IGeocodingService geocodingService;

	@Value("${myvariable}")
	private String myvariable;

	@GetMapping("/")
	public String healthCheck() {
		return "HEALTH CHECK OK!!!";
	}

	@GetMapping("/variable")
	public String variable() {
		log.debug("This is a test : variable = "+myvariable);
		return "Variable = "+myvariable;
	}

	@GetMapping("/geocode")
	public GeocodingResponseDto geocode(@RequestBody GeocodingRequestDto geocodingRequestDto) {


		GeocodingResponseDto geocodingResponseDto = geocodingService.geocode(geocodingRequestDto);

		CityBo c = new CityBo();
		c.setName(geocodingResponseDto.getCity());
		c.setCountry(geocodingResponseDto.getCountry());
		c.setState(geocodingResponseDto.getState());
		c.setCounty(geocodingResponseDto.getCounty());
		c.setLat(geocodingResponseDto.getLatitude());
		c.setLon(geocodingResponseDto.getLongitude());
		cityService.add(c);


		return geocodingResponseDto;
	}

	@GetMapping("/version")
	public String version() {
		return "The actual version is 1.0.0";
	}

	@GetMapping("/nations")
	public JsonNode getRandomNations() {
		var objectMapper = new ObjectMapper();
		var faker = new Faker(new Locale("en-US"));
		var nations = objectMapper.createArrayNode();
		for (var i = 0; i < 10; i++) {
			var nation = faker.nation();
			nations.add(objectMapper.createObjectNode()
					.put("nationality", nation.nationality())
					.put("capitalCity", nation.capitalCity())
					.put("flag", nation.flag())
					.put("language", nation.language()));
		}
		return nations;
	}

	@GetMapping("/currencies")
	public JsonNode getRandomCurrencies() {
		var objectMapper = new ObjectMapper();
		var faker = new Faker(new Locale("en-US"));
		var currencies = objectMapper.createArrayNode();
		for (var i = 0; i < 20; i++) {
			var currency = faker.currency();
			currencies.add(objectMapper.createObjectNode()
					.put("name", currency.name())
					.put("code", currency.code()));
		}
		return currencies;

	}

}
