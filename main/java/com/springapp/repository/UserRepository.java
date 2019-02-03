package com.springapp.repository;

import com.springapp.model.User;

import java.util.List;


public interface UserRepository {

	User createUser(User user);

	List<User> getUsers();

	User getUser(Integer id);

	boolean updateUser(User user);


	void deleteUser(Integer id);

    User getUserByName(String name);

	boolean ifUserNameExists(String name);

    void updateUserLastConnection(Integer id, String date);

	User getUserWithLevel(Integer user_id);

	boolean updateUserPointsAndLevel(Integer user_id, Integer new_points, Integer new_level);

	boolean updateUserPoints(Integer user_id, Integer new_points);

	int getUserIdByName(String username);

}