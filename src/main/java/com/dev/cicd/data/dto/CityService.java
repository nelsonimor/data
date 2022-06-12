package com.dev.cicd.data.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CityService implements ICityService {
	
	@Autowired
	private CityJpaRepository repo;

	@Override
	public void add(CityBo city) {
		repo.save(city);
		
	}

}
