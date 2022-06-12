package com.dev.cicd.data;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dev.cicd.data.dto.AddressResult;
import com.dev.cicd.data.dto.CityBo;
import com.dev.cicd.data.dto.CityJpaRepository;
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
	
	@GetMapping("/geocode/{adresse}")
	public AddressResult geocode(@PathVariable String adresse) {
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://nominatim.openstreetmap.org/?addressdetails=2&q="+adresse+"&format=json&limit=1&accept-language=EN";
		ResponseEntity<AddressResult[]> response= restTemplate.getForEntity(url, AddressResult[].class);
		
		
		CityBo c = new CityBo();
		c.setName(response.getBody()[0].getAddress().getTown());
		c.setLat(response.getBody()[0].getLat());
		c.setLon(response.getBody()[0].getLon());
		cityService.add(c);
		
		return response.getBody()[0];
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
