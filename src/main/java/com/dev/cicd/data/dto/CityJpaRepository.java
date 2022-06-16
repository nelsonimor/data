package com.dev.cicd.data.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.cicd.data.bo.CityBo;

@Repository
public interface CityJpaRepository extends JpaRepository<CityBo, Integer> {

}
