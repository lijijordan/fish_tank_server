package com.fish.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fish.dao.PHDao;
import com.fish.entity.PH;
import com.fish.service.PHService;

@Controller
@RequestMapping("/ph")
public class PHController {

	@Autowired
	private PHDao phDao;
	
	@Autowired
	private PHService phService;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public ModelAndView toBillList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("/jsp/ph-line");
		return model;
	}
	
	
	/**
	 * 获取最新的size条记录。
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<PH> getPHs(int size){
		return phDao.getPHs(size);
	}
	
	
	
	@RequestMapping(value = "/getPHs", method = RequestMethod.GET)
	@ResponseBody
	public List<PH> getPHs(String start, String end) throws ParseException{
//		System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(start));
//		System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(end));
		return phDao.getPHs(new Date(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(start).getTime() -  (1000 * 60 * 60 * 8)),
				new Date(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(end).getTime()-  (1000 * 60 * 60 * 8)));
	}
	
	
	/**
	 * 获取当前PH
	 * http://localhost:8080/fisher-web/ph/getCurrentPH
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getCurrentPH", method = RequestMethod.GET)
	@ResponseBody
	public PH getCurrentPH() throws ParseException{
		PH ph = this.phService.getCurrentPH();
		System.out.println(ph.getValue());
		return ph;
	}
	
	
	
	@RequestMapping("/put")
	public String put(@RequestParam(value="value", defaultValue="-1") String val){
		if(!val.equals("-1")){
			phDao.save(Float.valueOf(val));
		}
		return val;
	}
	
	
}