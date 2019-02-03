package com.springapp.repository.util;

import com.springapp.model.Daily;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DailyRowMapper implements RowMapper<Daily> {

	@Override
	public Daily mapRow(ResultSet rs, int rowNum) throws SQLException {
		Daily daily = new Daily();
		daily.setId(rs.getInt("id"));
		daily.setName(rs.getString("name"));
		daily.setUser_id(rs.getInt("user_id"));
		daily.setDone(rs.getBoolean("done"));
		return daily;
	}
}
