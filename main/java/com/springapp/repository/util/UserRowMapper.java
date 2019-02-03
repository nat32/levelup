package com.springapp.repository.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springapp.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setLevel_id(rs.getInt("level_id"));
		user.setPoints(rs.getInt("points"));
		user.setPassword(rs.getString("password"));
		return user;
	}
}
