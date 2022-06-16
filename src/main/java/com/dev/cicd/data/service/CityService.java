package com.dev.cicd.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dev.cicd.data.bo.CityBo;
import com.dev.cicd.data.dto.CityJpaRepository;

@Service
public class CityService implements ICityService {
	
	@Autowired
	private CityJpaRepository repo;

	@Override
	public void add(CityBo city) {
		repo.save(city);
		
	}

}
