package com.springapp.repository;

import com.springapp.model.Daily;
import com.springapp.repository.util.DailyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("dailyRepository")
public class DailyRepositoryImpl implements DailyRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Override
    public Daily createDaily(Daily daily){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        insert.setGeneratedKeyName("id");

        Map<String, Object> data = new HashMap();

        data.put("name", daily.getName());
        data.put("user_id", daily.getUser_id());

        List<String> columns = new ArrayList();
        columns.add("name");
        columns.add("user_id");

        insert.setTableName("daily");
        insert.setColumnNames(columns);

        Number id = insert.executeAndReturnKey(data);


        return getDaily(id.intValue());
    }

    @Override
    public Daily getDaily(Integer id) {
        Daily daily = jdbcTemplate.queryForObject("select * from daily where id = ?", new DailyRowMapper(), id);

        return daily;
    }

    @Override
    public List<Daily> getUserDailies(Integer id_user) {

        List<Daily> dailies = jdbcTemplate.query("select D.* from daily D where done = 0 and user_id = ? ", new DailyRowMapper(), id_user);

        return dailies;
    }

    @Override
    public List<Daily> getDoneUserDailies(Integer id_user) {

        List<Daily> dailies = jdbcTemplate.query("select D.* from daily D where done = 1 and user_id = ? ", new DailyRowMapper(), id_user);

        return dailies;
    }


    @Override
    public boolean updateDaily(Daily daily) {

        int done = jdbcTemplate.update("UPDATE daily set name = ? where id = ? ", daily.getName(),  daily.getId());

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean checkDaily(Integer daily_id) {
        int done = jdbcTemplate.update("UPDATE daily set done = 1 where id = ? ", daily_id);

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteDaily(Integer daily_id) {
        int done = jdbcTemplate.update("DELETE from daily  where id = ? ", daily_id);

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int countDailiesNotDone(Integer user_id) {


        String sql = "SELECT count(*) FROM daily D INNER JOIN users U on D.user_id = U.id WHERE D.done = 0 AND U.id = ? ";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { user_id }, Integer.class);

        return count;
    }


}
