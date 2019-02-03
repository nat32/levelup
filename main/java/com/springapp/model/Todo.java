package com.springapp.model;

import javax.validation.constraints.NotNull;

public class Todo {

    public Integer getDifficulty_id() {
        return difficulty_id;
    }

    public void setDifficulty_id(Integer difficulty_id) {
        this.difficulty_id = difficulty_id;
    }

    public String getDifficulty_name() {
        return difficulty_name;
    }

    public void setDifficulty_name(String difficulty_name) {
        this.difficulty_name = difficulty_name;
    }

    public Integer getDifficulty_points() {
        return difficulty_points;
    }

    public void setDifficulty_points(Integer difficulty_points) {
        this.difficulty_points = difficulty_points;
    }

    @NotNull
    private String difficulty_name;

    private Integer difficulty_points;

    private Integer difficulty_id;

    @NotNull
    private String name;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    private Integer user_id;

    private boolean done;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
