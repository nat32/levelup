package com.springapp.repository;

import com.springapp.model.Difficulty;
import com.springapp.model.Todo;

import java.util.List;

public interface TodoRepository {

	List<Todo> getTodos();

    List<Todo> getUserTodos(Integer id_user);

    Todo createTodo(Todo todo);

    Todo getTodoWithDifficulty(Integer id);

    Todo getTodo(Integer id);

    List<Difficulty> findAllDifficulties();

    boolean checkTodo(Integer todo_id);

    List<Todo> getUserDoneTodos(Integer id_user);

    boolean deleteTodo(Integer todo_id);

    int getTodoPoints(Integer todo_id);

    boolean updateTodo(Todo todo);

}