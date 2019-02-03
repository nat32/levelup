package com.springapp.service;


public interface DailyService {

    /**
     * Fonction qui vérifie le nombre de quotidiennes qui n'ont pas été validés la veille
     * Ensuite elle multiplie ce nombre par 5 ( points à gagner ou perdre de la quotidienne)
     * et retourne le résultat
     * @param user_id
     * @return
     */
    int checkDailiesAndGetPenalty(Integer user_id);
}
