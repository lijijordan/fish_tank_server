package com.fish.test;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.fish.dao.PHDao;
import com.fish.entity.PH;


public class PHDaoTest extends TestCase{
	
	private PHDao phDao;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ApplicationContext context = 
	    		new ClassPathXmlApplicationContext("DataSource.xml");
		DataSource dataSource = (DataSource)context.getBean("dataSource");
		phDao = new PHDao(dataSource);
	}
	
	public void testOne(){
		List<PH> list = phDao.getPHs(100);
		Assert.notEmpty(list);
		for (PH ph : list) {
			System.out.println(ph.getValue() + "     " + ph.getDate());
		}
	}
	
	public void testDate(){/*
		Date start = new Date(System.currentTimeMillis() - (1000 *  60 * 60 *24 ));
		Date end = new Date(System.currentTimeMillis() );
		System.out.println(start);
		System.out.println(end);
		List<PH> list = phDao.getPHs(start, end);
		Assert.notEmpty(list);
		for (PH ph : list) {
			System.out.println(ph.getValue() + "     " + ph.getDate());
		}
	*/}
	
}
