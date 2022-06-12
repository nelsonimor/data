package com.dev.cicd.data.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityJpaRepository extends JpaRepository<CityBo, Integer> {

}
