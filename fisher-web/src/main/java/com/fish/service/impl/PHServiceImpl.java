package com.fish.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.dao.PHDao;
import com.fish.entity.PH;
import com.fish.service.PHService;


@Service(value = "phService")
public class PHServiceImpl implements PHService{
	
	
	@Autowired
	private PHDao phDao;
	

	@Override
	public PH getCurrentPH() {
		return phDao.getCurrentPH();
	}


	@Override
	public List<PH> getPHs(int size) {
		return phDao.getPHs(size);
	}

}
