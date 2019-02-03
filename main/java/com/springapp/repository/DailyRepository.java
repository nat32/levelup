package com.springapp.repository;


import com.springapp.model.Daily;

import java.util.List;

public interface DailyRepository {

    Daily createDaily(Daily daily);

    Daily getDaily(Integer id);

    List<Daily> getUserDailies(Integer id_user);

    List<Daily> getDoneUserDailies(Integer id_user);

    boolean updateDaily(Daily daily);

    boolean checkDaily(Integer daily_id);

    boolean deleteDaily(Integer todo_id);

    int countDailiesNotDone(Integer user_id);


}