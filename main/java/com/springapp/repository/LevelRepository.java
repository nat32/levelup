package com.springapp.repository;

import com.springapp.model.Level;

import java.util.List;

public interface LevelRepository {

    Level getLevelByNumber(Integer level_number);

    Level createLevel(Level level);

    List<Level> findAllLevels();
}