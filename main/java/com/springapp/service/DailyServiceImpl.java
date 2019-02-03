package com.springapp.service;

import com.springapp.repository.DailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springapp.controller.DailyController.DAILY_POINTS;

@Service("dailyService")
public class DailyServiceImpl implements DailyService {

    @Autowired
    private DailyRepository dailyRepository;


    @Override
    public int checkDailiesAndGetPenalty(Integer user_id) {

        /*
        Get number of Dailies Not Checked
         */

        int dailies_not_done = dailyRepository.countDailiesNotDone(user_id);

        if(dailies_not_done > 0){
            int penalty_points = dailies_not_done * DAILY_POINTS;

            return penalty_points;
        }else{
            return 0;
        }


    }


}
