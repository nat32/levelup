package com.springapp.repository.util;

import com.springapp.model.Prize;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrizeLevelRowMapper implements RowMapper<Prize> {

	@Override
	public Prize mapRow(ResultSet rs, int rowNum) throws SQLException {
		Prize prize = new Prize();
		prize.setId(rs.getInt("id"));
		prize.setName(rs.getString("name"));
		prize.setUser_id(rs.getInt("user_id"));
		prize.setLevel_id(rs.getInt("level_id"));

		prize.setLevel_number(rs.getInt("level_number"));

		prize.setLevel_points(rs.getInt("level_points"));

		return prize;
	}

}
