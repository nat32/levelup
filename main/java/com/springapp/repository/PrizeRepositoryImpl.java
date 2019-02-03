package com.springapp.repository;

import com.springapp.model.Prize;
import com.springapp.repository.util.PrizeLevelRowMapper;
import com.springapp.repository.util.PrizeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("prizeRepository")
public class PrizeRepositoryImpl implements PrizeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

    @Override
    public Prize createPrize(Prize prize){
	    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        insert.setGeneratedKeyName("id");

        Map<String, Object> data = new HashMap();

        data.put("name", prize.getName());
        data.put("user_id", prize.getUser_id());
        data.put("level_id", prize.getLevel_id());

        List<String> columns = new ArrayList();
        columns.add("name");
        columns.add("user_id");
        columns.add("level_id");

        insert.setTableName("prize");
        insert.setColumnNames(columns);

        Number id = insert.executeAndReturnKey(data);

        return getPrize(id.intValue());
    }

    @Override
    public Prize getPrize(Integer id) {
        Prize prize = jdbcTemplate.queryForObject("select * from prize where id = ?", new PrizeRowMapper(), id);

        return prize;
    }

    @Override
    public List<Prize> getWonPrizes(Integer level_id, Integer user_id) {

        List<Prize> prizes = jdbcTemplate.query("select * from prize where level_id = ? AND user_id = ?", new PrizeRowMapper(), level_id, user_id);

        return prizes;
    }

    @Override
    public List<Prize> getUserPrizes(Integer user_id) {

        List<Prize> prizes = jdbcTemplate.query("select P.*, L.number as level_number, L.points as level_points from prize P inner join level L ON P.level_id = L.id where won = 0 and user_id = ? ", new PrizeLevelRowMapper(), user_id);

        return prizes;
    }

    @Override
    public List<Prize> getUserWonPrizes(Integer user_id) {

        List<Prize> prizes = jdbcTemplate.query("select P.*, L.number as level_number, L.points as level_points from prize P inner join level L ON P.level_id = L.id where won = 1 and user_id = ? ", new PrizeLevelRowMapper(), user_id);

        return prizes;
    }

    @Override
    public Prize getPrizeWithLevel(Integer prize_id) {
        Prize prize = jdbcTemplate.queryForObject("select P.*, L.number as level_number, L.points as level_points from prize P inner join level L ON P.level_id = L.id where P.id = ? ", new PrizeLevelRowMapper(), prize_id);

        return prize;
    }

    @Override
    public boolean updatePrize(Prize prize) {

        int done = jdbcTemplate.update("UPDATE prize set name = ?, level_id = ? where id = ? ", prize.getName(), prize.getLevel_id(), prize.getId());

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public boolean deletePrize(Integer prize_id) {
        int done = jdbcTemplate.update("DELETE from prize  where id = ? ", prize_id);

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public Integer checkLevelPrizes(Integer level_id, Integer user_id){

        String sql = "SELECT count(P.id) FROM prize P WHERE P.won = 0 AND P.level_id = ? AND P.user_id = ? ";

        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { level_id, user_id }, Integer.class);

        return count;
    }

    @Override
    public void updateWonPrizes(List<Prize> prizes) {

        for (final Prize prize : prizes) {
           jdbcTemplate.update("UPDATE prize set won = 1 where id = ? ", prize.getId());
        }
    }

}
