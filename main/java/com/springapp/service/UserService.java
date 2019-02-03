package com.springapp.service;

import com.springapp.model.User;

import java.util.List;


public interface UserService {

    User createUser(User user);

    List<User> getUsers();

    User getUser(Integer id);

    User getUserWithlevel(Integer user_id);

    User getUserByName(String name);

    boolean updateUser(User user);

    void deleteUser(Integer id);

    /**
     * Fonction qui vérifie si le nom utilisateur renseigné existe dans la base de données
     * @param name
     * @return
     */
    boolean ifUserNameExists(String name);

    /**
     * Fonction qui mets à jour la date connexion de l'utilisateur
     * @param user_id
     * @param date
     */
    void updateUserLastConnection(Integer user_id, String date);

    /**
     * Fonction qui selon les points gagnes par l'utilisateur
     * soit seulement ajoute les points à son solde
     * soit lui permet de gagner un niveau
     * @param user_id
     * @param points
     * @return
     */
    boolean updateUserPointsAndLevel(Integer user_id, Integer points);

    /**
     * Fonction qui selon de points perdus par l'utilisateur
     * soit seulment soustrait les points de son solde
     * soit lui fait perdre un niveau
     * @param user_id
     * @param penalty_points
     * @return
     */
    boolean subtractPointsAndUpdateLevel(Integer user_id, Integer penalty_points);

    int getUserIdByName(String username);


}
