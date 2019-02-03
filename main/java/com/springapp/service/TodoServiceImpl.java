package com.springapp.service;

import com.springapp.model.Difficulty;
import com.springapp.model.Todo;
import com.springapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;



    @Override
    public List<Todo> getUserTodos(Integer id_user) {

        return todoRepository.getUserTodos(id_user);
    }

    @Override
    public List<Todo> getUserDoneTodos(Integer id_user) {
        return todoRepository.getUserDoneTodos(id_user);
    }

    @Override
    public Todo createTodo(Todo todo) {
        return  todoRepository.createTodo(todo);
    }

    @Override
    public boolean deleteTodo(Integer todo_id) {
        return todoRepository.deleteTodo(todo_id);
    }

    @Override
    public Todo getTodoWithDifficulty(Integer todo_id) {
        return todoRepository.getTodoWithDifficulty(todo_id);
    }

    @Override
    public boolean updateTodo(Todo todo) {
        return todoRepository.updateTodo(todo);
    }

    @Override
    public int checkTodoAndGetPoints(Integer todo_id) {

        boolean  checked_todo = todoRepository.checkTodo(todo_id);

        if(checked_todo){
            return todoRepository.getTodoPoints(todo_id);
        }else{
            return 0;
        }

    }

    @Override
    public List<Difficulty> findAllDifficulties(){

        List<Difficulty> difficulties = todoRepository.findAllDifficulties();

        return difficulties;
    }


    @Override
    public List<Todo> getTodos() {
        return todoRepository.getTodos();
    }
}
