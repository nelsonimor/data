package com.dev.cicd.data.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "T_CITY")
public class CityBo {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String name;
	
	
	@NotNull
	private String country;
	
	@NotNull
	private String state;
	
	private String county;
	
	@NotNull
	private Double lat;
	
	@NotNull
	private Double lon;


}
