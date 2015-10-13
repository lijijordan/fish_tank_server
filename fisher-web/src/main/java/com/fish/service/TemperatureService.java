package com.fish.service;

import java.util.List;

import com.fish.entity.Temperature;

public interface TemperatureService {

	Temperature getCurrentTemp();

	List<Temperature> getTemps(int size);

}
