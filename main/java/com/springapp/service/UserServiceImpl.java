package com.springapp.service;


import com.springapp.model.Level;
import com.springapp.model.User;
import com.springapp.repository.LevelRepository;
import com.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Override
    public User createUser(User user) {

        return userRepository.createUser(user);
    }

    @Override
    public List<User> getUsers() {

        return userRepository.getUsers();
    }


    @Override
    public User getUser(Integer id){

       return userRepository.getUser(id);
    }

    @Override
    public User getUserWithlevel(Integer id) {
         return userRepository.getUserWithLevel(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }


    @Override
    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }


    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteUser(id);
    }

    @Override
    public boolean ifUserNameExists(String name) {
       return userRepository.ifUserNameExists(name);
    }

    @Override
    public void updateUserLastConnection(Integer id, String date) {
        userRepository.updateUserLastConnection(id, date);
    }

    @Override
    public boolean updateUserPointsAndLevel(Integer user_id, Integer points) {

        User user  =  userRepository.getUserWithLevel(user_id);

        int old_points = user.getPoints();

        int new_points = old_points + points;

        int level_id = user.getLevel_id();

        int level_number = user.getLevel_number();

        int next_level_number = level_number + 1;

        Level level = levelRepository.getLevelByNumber(next_level_number);

        int next_level_points = level.getPoints();

        if(new_points >= next_level_points){

            int reste = new_points - next_level_points;

            int next_level_id = level.getId();

            boolean updated = userRepository.updateUserPointsAndLevel(user_id, reste, next_level_id);
            return updated;

        }else{

            boolean updated =  userRepository.updateUserPoints(user_id, new_points);

            return updated;
        }


    }

    @Override
    public boolean subtractPointsAndUpdateLevel(Integer user_id, Integer penalty_points) {

        User user  =  userRepository.getUserWithLevel(user_id);

        Integer old_points = user.getPoints();

        Integer new_points = old_points - penalty_points;

        if(new_points <= 0){

            Integer level_number = user.getLevel_number();

            if(level_number != 1){

                Integer before_level_number = level_number - 1 ;

                Level level = levelRepository.getLevelByNumber(before_level_number);

                Integer before_level_id = level.getId();

                boolean updated = userRepository.updateUserPointsAndLevel(user_id, 0, before_level_id);

                return updated;
            }else{

                boolean updated =  userRepository.updateUserPoints(user_id, 0);
                return updated;
            }


        }else{

            boolean updated =  userRepository.updateUserPoints(user_id, new_points);

            return updated;
        }


    }


    @Override
    public int getUserIdByName(String username){

        return userRepository.getUserIdByName(username);
    }
}
