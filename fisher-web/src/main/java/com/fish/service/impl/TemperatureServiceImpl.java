package com.fish.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.dao.TemperatureDao;
import com.fish.entity.Temperature;
import com.fish.service.TemperatureService;


@Service(value = "tempService")
public class TemperatureServiceImpl implements TemperatureService{

	@Autowired
	private TemperatureDao temperatureDao;
	
	@Override
	public Temperature getCurrentTemp() {
		return temperatureDao.getCurrentTemp();
	}

}
