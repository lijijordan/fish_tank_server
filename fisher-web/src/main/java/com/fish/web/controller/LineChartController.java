package com.fish.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fish.dao.PHDao;
import com.fish.dao.TemperatureDao;

/**
 * 
 * @author lijib
 *
 */
@Controller
@RequestMapping("/line")
public class LineChartController {

	@Autowired
	private PHDao phDao;
	
	@Autowired
	private TemperatureDao temperatureDao;
	
//	@Autowired
//	private RawDao rawDao;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public Object getPHs(String start, String end, String model) throws ParseException{
		if(model.equals("ph")){
			return phDao.getPHs(new Date(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(start).getTime() -  (1000 * 60 * 60 * 8)),
					new Date(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(end).getTime()-  (1000 * 60 * 60 * 8)));
		}else if(model.equals("temperature")){
		}else if(model.equals("raw")){
//			return rawDao.getRaws(new Date(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(start).getTime() -  (1000 * 60 * 60 * 8)),
//					new Date(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(end).getTime()-  (1000 * 60 * 60 * 8)));
		}
		return null;
	}
	
}