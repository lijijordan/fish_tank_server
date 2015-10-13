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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fish.entity.Temperature;


@Repository(value = "temperatureDao")
public class TemperatureDao {

	Logger log = Logger.getLogger(TemperatureDao.class);
	
	@Autowired
	DataSource dataSource;
	
	public TemperatureDao() {
		
	}
	
	public TemperatureDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(float value){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "INSERT INTO `fisher`.`Temperature` (`value`, `time`) VALUES (?,?);";
		log.debug(sql);
		template.update(sql, new Object[]{value, new Date(System.currentTimeMillis())});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Temperature> getTemps(int size){
		List<Temperature> list = new ArrayList<Temperature>();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT * FROM fisher.Temperature order by time desc limit ?;";
		list = template.query(sql, new Object[]{size}, new RowMapper() {
			public Temperature mapRow(ResultSet rs, int rowNum) throws SQLException {
				Temperature temp = new Temperature();
				temp.setId(rs.getInt(1));
				temp.setValue(rs.getFloat(2));
				temp.setDate(rs.getTimestamp(3));
				return temp;
			}
		});
		return list;
	}

	public Temperature getCurrentTemp() {
		Temperature t = null;
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "select value, time from temperature  order by time desc limit 1;";
		t = (Temperature) template.queryForObject(sql, new RowMapper<Temperature>(){
			@Override
			public Temperature mapRow(ResultSet rs, int rowNum) throws SQLException {
				Temperature t = new Temperature();
				t.setValue(rs.getFloat(1));
				t.setDate(rs.getDate(2));
				return t;
			}
		});
		return t;
	}
	

}
