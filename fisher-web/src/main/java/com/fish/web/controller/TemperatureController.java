package com.fish.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fish.dao.TemperatureDao;

@Controller
@RequestMapping("/temp")
public class TemperatureController {

	@Autowired
	private TemperatureDao temperatureDao;
	
	
	@RequestMapping("/put")
	public String put(@RequestParam(value="value", defaultValue="-1") String val){
		if(!val.equals("-1")){
			temperatureDao.save(Float.valueOf(val));
		}
		return val;
	}
	
}