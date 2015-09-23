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

import com.fish.entity.Raw;

@Repository(value = "rawDao")
public class RawDao {
	
	@Autowired
	DataSource dataSource;
	
	public RawDao() {
		
	}
	
	public RawDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(float value){
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "INSERT INTO `fisher`.`raw` (`value`, `time`) VALUES (?,?);";
		template.update(sql, new Object[]{value, new Date(System.currentTimeMillis())});
	}
	
	public List<Raw> getRaws(Date start, Date end) {
		List<Raw> list = new ArrayList<Raw>();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		String sql = "SELECT * FROM fisher.raw where time > ? and time < ? order by time desc ;";
		list = template.query(sql, new Object[]{start, end}, new RowMapper<Raw>() {
			public Raw mapRow(ResultSet rs, int rowNum) throws SQLException {
				Raw ph = new Raw();
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
