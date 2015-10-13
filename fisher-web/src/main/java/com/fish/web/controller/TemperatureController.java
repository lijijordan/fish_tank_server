package com.fish.web.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fish.dao.TemperatureDao;
import com.fish.entity.Temperature;
import com.fish.service.TemperatureService;

@Controller
@RequestMapping("/temp")
public class TemperatureController {

	@Autowired
	private TemperatureDao temperatureDao;
	
	@Autowired
	private TemperatureService tempService;
	
	
	
	@RequestMapping("/put")
	public String put(@RequestParam(value="value", defaultValue="-1") String val){
		if(!val.equals("-1")){
			temperatureDao.save(Float.valueOf(val));
		}
		return val;
	}
	
	
	/**
	 * 获取最新的size条记录。
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Temperature> getTemps(int size){
		return tempService.getTemps(size);
	}
	
	/**
	 * 获取当前温度
	 * http://localhost:8080/fisher-web/temp/getCurrentTemp
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getCurrentTemp", method = RequestMethod.GET)
	@ResponseBody
	public Temperature getCurrentTemp() throws ParseException{
		Temperature t = this.tempService.getCurrentTemp();
		System.out.println(t.getValue());
		return t;
	}
	
}