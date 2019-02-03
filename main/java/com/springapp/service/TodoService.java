package com.springapp.service;

import com.springapp.model.Difficulty;
import com.springapp.model.Todo;

import java.util.List;

public interface TodoService {

    List<Difficulty> findAllDifficulties();

    List<Todo> getUserTodos(Integer id_user);

    List<Todo> getUserDoneTodos(Integer id_user);

    Todo createTodo(Todo todo);

    boolean deleteTodo(Integer todo_id);

    Todo getTodoWithDifficulty(Integer todo_id);

    boolean updateTodo(Todo todo);

    /**
     * Fonction qui valide une tâche à faire et récupère les points associes à sa difficulte
     * @param todo_id
     * @return
     */
    int checkTodoAndGetPoints(Integer todo_id);

    List<Todo> getTodos();
}
