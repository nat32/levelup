package com.springapp.repository.util;

import com.springapp.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLevelRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setLevel_id(rs.getInt("level_id"));
		user.setPassword(rs.getString("password"));
		user.setPoints(rs.getInt("points"));
		user.setLevel_number(rs.getInt("level_number"));
		user.setLast_connection(rs.getString("last_connection"));

		return user;
	}
}
