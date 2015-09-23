package com.fish.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fish.entity.PH;

@Repository(value = "phDao")
public class PHDao{
	
	@Autowired
	DataSource dataSource;
	
	public PHDao() {
		
	}
	
	public PHDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(float value){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "INSERT INTO `fisher`.`PH` (`value`, `time`) VALUES (?,?);";
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
	
}
