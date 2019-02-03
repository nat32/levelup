package com.springapp.repository.util;

import com.springapp.model.Todo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TodoDifficultyRowMapper implements RowMapper<Todo> {

	@Override
	public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Todo todo = new Todo();
		todo.setId(rs.getInt("id"));
		todo.setName(rs.getString("name"));
		todo.setUser_id(rs.getInt("user_id"));
		todo.setDifficulty_id(rs.getInt("difficulty_id"));

		todo.setDifficulty_name(rs.getString("difficulty_name"));

		todo.setDifficulty_points(rs.getInt("difficulty_points"));

		return todo;
	}

}
