package com.fish.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fish.service.PHService;
import com.fish.service.TemperatureService;

@Controller
@RequestMapping("/phone")
public class PhoneController {
	
	private static final int SIZE = 10;
	
	@Autowired
	private TemperatureService tempService;
	
	@Autowired
	private PHService phService;

	/**
	 * 获取最新的size条记录。
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get(){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ph", phService.getPHs(SIZE));
		result.put("temp", tempService.getTemps(SIZE));
		return result;
	}
	
}