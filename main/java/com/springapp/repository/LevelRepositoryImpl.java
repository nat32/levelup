package com.springapp.repository;


import com.springapp.model.Level;
import com.springapp.repository.util.LevelRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("levelRepository")
public class LevelRepositoryImpl implements LevelRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;


    @Override
    public Level getLevelByNumber(Integer level_number) {

        Level level  = jdbcTemplate.queryForObject("select *  from level  where number = ? ", new LevelRowMapper(), level_number);

        return level;

    }

    @Override
    public Level createLevel(Level level) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        insert.setGeneratedKeyName("id");

        Map<String, Object> data = new HashMap();

        data.put("number", level.getNumber());
        data.put("points", level.getPoints());

        List<String> columns = new ArrayList();
        columns.add("number");
        columns.add("points");


        insert.setTableName("level");
        insert.setColumnNames(columns);
        Number id = insert.executeAndReturnKey(data);

        return getLevelByNumber( level.getNumber());

    }

    @Override
    public List<Level> findAllLevels() {

        List<Level> levels  = jdbcTemplate.query("select *  from level  ", new LevelRowMapper());

        return levels;
    }



}
