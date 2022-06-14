package com.dev.cicd.data;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dev.cicd.data.dto.AddressResult;
import com.dev.cicd.data.dto.CityBo;
import com.dev.cicd.data.dto.CityJpaRepository;
import com.dev.cicd.data.dto.GeocodingRequestDto;
import com.dev.cicd.data.dto.GeocodingResponseDto;
import com.dev.cicd.data.dto.ICityService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class DataController {
	
	@Autowired
	private ICityService cityService;

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
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://nominatim.openstreetmap.org/?addressdetails=2&q="+geocodingRequestDto.getCity()+","+geocodingRequestDto.getCountry()+"&format=json&limit=1&accept-language=EN";
		ResponseEntity<AddressResult[]> response= restTemplate.getForEntity(url, AddressResult[].class);
		
		
		AddressResult[] results = response.getBody();
		AddressResult result = results[0];
		
		
		GeocodingResponseDto geocodingResponseDto = new GeocodingResponseDto();
		geocodingResponseDto.setCity(result.getAddress().getCity());
		geocodingResponseDto.setCountry(result.getAddress().getCountry());
		geocodingResponseDto.setState(result.getAddress().getState());
		geocodingResponseDto.setCounty(result.getAddress().getCounty());
		geocodingResponseDto.setPostcode(result.getAddress().getPostcode());
		geocodingResponseDto.setLatitude(result.getLat());
		geocodingResponseDto.setLongitude(result.getLon());
		
		
		CityBo c = new CityBo();
		c.setName(response.getBody()[0].getAddress().getCity());
		c.setCountry(response.getBody()[0].getAddress().getCountry());
		c.setState(response.getBody()[0].getAddress().getState());
		c.setCounty(response.getBody()[0].getAddress().getCounty());
		c.setPostcode(response.getBody()[0].getAddress().getPostcode());
		c.setLat(response.getBody()[0].getLat());
		c.setLon(response.getBody()[0].getLon());
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
