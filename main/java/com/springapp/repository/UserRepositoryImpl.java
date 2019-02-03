package com.springapp.repository;

import com.springapp.model.User;
import com.springapp.repository.util.UserLevelRowMapper;
import com.springapp.repository.util.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public User createUser(User user) {

		SimpleJdbcInsert insert_user = new SimpleJdbcInsert(jdbcTemplate);

		insert_user.setGeneratedKeyName("id");

		Map<String, Object> data_user = new HashMap();

		data_user.put("username", user.getUsername());
		data_user.put("password", user.getPassword());
		data_user.put("last_connection", user.getLast_connection());

		List<String> columns_user = new ArrayList();
		columns_user.add("username");
		columns_user.add("password");
		columns_user.add("last_connection");

		insert_user.setTableName("users");
		insert_user.setColumnNames(columns_user);
		Number id_user = insert_user.executeAndReturnKey(data_user);

		//adding authority
		SimpleJdbcInsert insert_authority = new SimpleJdbcInsert(jdbcTemplate);

		Map<String, Object> data_authority = new HashMap();

		data_authority.put("username", user.getUsername());
		data_authority.put("authority", "ROLE_USER");

		List<String> columns_authority = new ArrayList();

		columns_authority.add("username");
		columns_authority.add("authority");
		insert_authority.setTableName("authorities");
		insert_authority.setColumnNames(columns_authority);
		int id = insert_authority.execute(data_authority);

		return getUser(id_user.intValue());
	}

	@Override
	public User getUserByName(String name) {
		User user = jdbcTemplate.queryForObject("select U.*, L.number as level_number from users U inner join level L ON U.level_id = L.id where U.username= ?", new UserLevelRowMapper(), name);

		return user;
	}

	@Override
	public boolean ifUserNameExists(String name) {
		String sql = "SELECT count(*) FROM users WHERE username = ?";
		boolean result = false;
		int count = jdbcTemplate.queryForObject(sql, new Object[] { name }, Integer.class);
		if (count > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public void updateUserLastConnection(Integer id, String date) {


		jdbcTemplate.update("UPDATE users set last_connection = ? WHERE id = ?", date, id);

	}

	@Override
	public User getUserWithLevel(Integer user_id) {

		User user = jdbcTemplate.queryForObject("select U.*, L.number as level_number from users U inner join level L ON U.level_id = L.id where U.id = ?", new UserLevelRowMapper(), user_id);

		return user;

	}

	@Override
	public boolean updateUserPointsAndLevel(Integer user_id, Integer new_points, Integer new_level) {

		int done = jdbcTemplate.update("UPDATE users set points = ?, level_id = ? where id = ? ",  new_points,new_level, user_id);


		if(done == 1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateUserPoints(Integer user_id, Integer new_points) {

		int done = jdbcTemplate.update("UPDATE users set points = ? where id = ? ",  new_points, user_id );

		if(done == 1){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public User getUser(Integer id) {
		User user = jdbcTemplate.queryForObject("select * from users where id = ?", new UserRowMapper(), id);

		return user;
	}

	@Override
	public boolean updateUser(User user) {

		int done = jdbcTemplate.update("UPDATE users set username = ? WHERE id = ?",
				user.getUsername(), user.getId());

		if(done == 1){
			return true;
		}else{
			return false;
		}

	}


	@Override
	public void deleteUser(Integer id) {
		jdbcTemplate.update("DELETE from users where id = ? ", id);
	}



	@Override
	public List<User> getUsers() {

		List<User> users = jdbcTemplate.query("select * from users", new UserRowMapper());

		return users;
	}

	@Override
	public int getUserIdByName(String username) {


		String sql = "SELECT id FROM users WHERE username = ?";

		int id = (int)jdbcTemplate.queryForObject(
				sql, new Object[] { username }, Integer.class);

		return id;
	}

}
