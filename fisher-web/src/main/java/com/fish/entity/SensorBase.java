package com.fish.entity;

import java.util.Date;

public class SensorBase {
	
	private int id;
	
	private float value;
	
	private Date date;
	
	/*
	 * 小时（统计）
	 */
	private int hour;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}
	
}
