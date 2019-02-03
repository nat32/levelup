package com.springapp.repository.util;

import com.springapp.model.Difficulty;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DifficultyRowMapper implements RowMapper<Difficulty> {

	@Override
	public Difficulty mapRow(ResultSet rs, int rowNum) throws SQLException {
		Difficulty difficulty = new Difficulty();
		difficulty.setId(rs.getInt("id"));
		difficulty.setName(rs.getString("name"));
		difficulty.setPoints(rs.getInt("points"));
		return difficulty;
	}
}
