package com.springapp.repository;

import com.springapp.model.Prize;

import java.util.List;

public interface PrizeRepository {

    Prize createPrize(Prize prize);

    Prize getPrize(Integer id);

    List<Prize> getWonPrizes(Integer level_id, Integer user_id);

    List<Prize> getUserPrizes(Integer user_id);

    List<Prize> getUserWonPrizes(Integer user_id);

    Prize getPrizeWithLevel(Integer prize_id);

    boolean updatePrize(Prize prize);

    boolean deletePrize(Integer prize_id);

    Integer checkLevelPrizes(Integer level_id, Integer user_id);

    void updateWonPrizes(List<Prize> prizes);


}