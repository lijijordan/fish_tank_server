package com.fish.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fish.entity.PH;

@Repository(value = "phDao")
public class PHDao{
	
	@Autowired
	DataSource dataSource;
	
	Logger log = Logger.getLogger(PHDao.class);
	
	public PHDao() {
		
	}
	
	public PHDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(float value){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "INSERT INTO `fisher`.`PH` (`value`, `time`) VALUES (?,?);";
		log.debug(sql);
		template.update(sql, new Object[]{value, new Date(System.currentTimeMillis())});
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PH> getPHs(int size){
		List<PH> list = new ArrayList<PH>();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT * FROM fisher.PH order by time desc limit ?;";
		list = template.query(sql, new Object[]{size}, new RowMapper() {
			public PH mapRow(ResultSet rs, int rowNum) throws SQLException {
				PH ph = new PH();
				ph.setId(rs.getInt(1));
				ph.setValue(rs.getFloat(2));
				ph.setDate(rs.getTimestamp(3));
				return ph;
			}
		});
		return list;
	}

	public List<PH> getPHs(Date start, Date end) {
		List<PH> list = new ArrayList<PH>();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT * FROM fisher.PH where time > ? and time < ? order by time desc ;";
		list = template.query(sql, new Object[]{start, end}, new RowMapper<PH>() {
			public PH mapRow(ResultSet rs, int rowNum) throws SQLException {
				PH ph = new PH();
				ph.setId(rs.getInt(1));
				ph.setValue(rs.getFloat(2));
				// adjust time later 8 hours
				ph.setDate(new Date(rs.getTimestamp(3).getTime() +  (1000 * 60 * 60 * 8)));
				return ph;
			}
		});
		return list;
	}

	/**
	 */
	public PH getCurrentPH() {
		PH ph = null;
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "select value, time from ph order by time desc limit 1;";
		ph = (PH) template.queryForObject(sql, new RowMapper<PH>(){
			@Override
			public PH mapRow(ResultSet rs, int rowNum) throws SQLException {
				PH ph = new PH();
				ph.setValue(rs.getFloat(1));
				ph.setDate(rs.getDate(2));
				return ph;
			}
		});
		return ph;
	}
	
	/**
	 * 按小时统计平均值
	 * 如果start == null 或者 end == null 则统计所有的历史数据
	 */
	public PH getPHGroupByHours(Date start, Date end) {
		PH ph = null;
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "select min(value), DATE_FORMAT(time, '%k') from ph "
				+ " where ph.time > ? and ph.time < ? "
				+ " group by DATE_FORMAT(time, '%k') order by time desc";
		
		if(start == null || end == null){
			sql = "select min(value), DATE_FORMAT(time, '%k') from ph "
					+ " group by DATE_FORMAT(time, '%k') order by time desc";
		}
		ph = (PH) template.queryForObject(sql, new RowMapper<PH>(){
			@Override
			public PH mapRow(ResultSet rs, int rowNum) throws SQLException {
				PH ph = new PH();
				ph.setValue(rs.getFloat(1));
				ph.setHour(Integer.parseInt(rs.getString(2)));
				return ph;
			}
		}, new Object[]{start, end});
		return ph;
	}
	
}
