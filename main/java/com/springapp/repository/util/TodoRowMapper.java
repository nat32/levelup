package com.springapp.repository.util;

import com.springapp.model.Todo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoRowMapper implements RowMapper<Todo> {

	@Override
	public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Todo todo = new Todo();
		todo.setId(rs.getInt("id"));
		todo.setName(rs.getString("name"));
		todo.setUser_id(rs.getInt("user_id"));
		todo.setDifficulty_id(rs.getInt("difficulty_id"));
		todo.setDone(rs.getBoolean("done"));

		return todo;
	}
}
