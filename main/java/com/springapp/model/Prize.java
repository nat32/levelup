package com.springapp.model;


import javax.validation.constraints.NotNull;

public class Prize {

    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer level_id;

    private Integer user_id;

    public Integer getLevel_id() {
        return level_id;
    }

    public void setLevel_id(Integer level_id) {
        this.level_id = level_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    private boolean won;


    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    private Integer level_number;

    private Integer level_points;

    public Integer getLevel_number() {
        return level_number;
    }

    public void setLevel_number(Integer level_number) {
        this.level_number = level_number;
    }

    public Integer getLevel_points() {
        return level_points;
    }

    public void setLevel_points(Integer level_points) {
        this.level_points = level_points;
    }
}
