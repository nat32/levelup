package com.springapp.repository;

import com.springapp.model.Difficulty;
import com.springapp.model.Todo;
import com.springapp.repository.util.DifficultyRowMapper;
import com.springapp.repository.util.TodoDifficultyRowMapper;
import com.springapp.repository.util.TodoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("todoRepository")
public class TodoRepositoryImpl implements TodoRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Todo> getTodos() {
		Todo todo = new Todo();
		todo.setName("payer l'edf");
		todo.setDifficulty_id(3);
        todo.setDifficulty_points(30);
        todo.setDifficulty_name("Difficile");
		List <Todo> todos = new ArrayList();
		todos.add(todo);
		return todos;
	}

	@Override
	public List<Todo> getUserTodos(Integer id_user) {

		List<Todo> todos = jdbcTemplate.query("select TD.*, D.name as difficulty_name, D.points as difficulty_points from todo TD inner join difficulty D ON TD.difficulty_id = D.id where done = 0 and user_id = ? ", new TodoDifficultyRowMapper(), id_user);

		return todos;

	}

    @Override
    public Todo createTodo(Todo todo){

	    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        insert.setGeneratedKeyName("id");

        Map<String, Object> data = new HashMap();

        data.put("name", todo.getName());
        data.put("user_id", todo.getUser_id());
        data.put("difficulty_id", todo.getDifficulty_id());

        List<String> columns = new ArrayList();
        columns.add("name");
        columns.add("user_id");
        columns.add("difficulty_id");

        insert.setTableName("todo");
        insert.setColumnNames(columns);

        Number id = insert.executeAndReturnKey(data);

        return getTodo(id.intValue());
    }

    @Override
    public Todo getTodoWithDifficulty(Integer id) {

        Todo todo = jdbcTemplate.queryForObject("select TD.*, D.name as difficulty_name, D.points as difficulty_points from todo TD inner join difficulty D ON TD.difficulty_id = D.id where TD.id = ? ", new TodoDifficultyRowMapper(), id);

        return todo;
    }


    @Override
    public Todo getTodo(Integer id) {
        Todo todo = jdbcTemplate.queryForObject("select * from todo where id = ?", new TodoRowMapper(), id);

        return todo;
    }

    @Override
    public List<Difficulty> findAllDifficulties() {
        List<Difficulty> difficulties = jdbcTemplate.query("select * from difficulty ", new DifficultyRowMapper());

        return difficulties;
    }

    @Override
    public boolean checkTodo(Integer todo_id){
        int done = jdbcTemplate.update("UPDATE todo set done = 1 where id = ? ", todo_id);

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getTodoPoints(Integer todo_id) {


        Todo todo = jdbcTemplate.queryForObject("select TD.*, D.name as difficulty_name, D.points as difficulty_points from todo TD inner join difficulty D ON TD.difficulty_id = D.id where TD.id = ? ", new TodoDifficultyRowMapper(), todo_id);

        int points = todo.getDifficulty_points();

        return points;

    }


    @Override
    public boolean updateTodo(Todo todo) {

        int done = jdbcTemplate.update("UPDATE todo set name = ?, difficulty_id = ? where id = ? ", todo.getName(), todo.getDifficulty_id(), todo.getId());

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }



    @Override
    public List<Todo> getUserDoneTodos(Integer id_user) {

        List<Todo> todos = jdbcTemplate.query("select TD.*, D.name as difficulty_name, D.points as difficulty_points from todo TD inner join difficulty D ON TD.difficulty_id = D.id where done = 1 and user_id = ? ", new TodoDifficultyRowMapper(), id_user);

        return todos;

    }

    @Override
    public boolean deleteTodo(Integer todo_id) {
        int done = jdbcTemplate.update("DELETE from todo  where id = ? ", todo_id);

        if(done == 1){
            return true;
        }else{
            return false;
        }
    }

}
