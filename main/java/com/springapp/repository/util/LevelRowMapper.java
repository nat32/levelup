package com.springapp.repository.util;

import com.springapp.model.Level;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelRowMapper implements RowMapper<Level> {

	@Override
	public Level mapRow(ResultSet rs, int rowNum) throws SQLException {
		Level level = new Level();
		level.setId(rs.getInt("id"));
		level.setNumber(rs.getInt("number"));
		level.setPoints(rs.getInt("points"));
		return level;
	}
}
