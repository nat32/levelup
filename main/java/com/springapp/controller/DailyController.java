package com.springapp.controller;

import com.springapp.model.Daily;
import com.springapp.model.User;
import com.springapp.repository.DailyRepository;
import com.springapp.repository.PrizeRepository;
import com.springapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
public class DailyController {

    public static final int DAILY_POINTS = 5;

    @Autowired
    private UserService userService;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private PrizeRepository prizeRepository;

    /**
     * Fonction qui affiche le formulaire pour ajoute une tache quotidienne
     * @param user_id
     * @param daily
     * @param model
     * @return
     */
    @RequestMapping(value = "/addDaily/{user_id}", method = RequestMethod.GET)
    public String addDaily(@PathVariable(value = "user_id") Integer user_id, @ModelAttribute ("daily") Daily daily,
                           ModelMap model, HttpServletRequest request)
    {

        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);

        if(userIdAuth == user_id) {
            model.addAttribute("user_id", user_id);
            return "addDaily";
        } else {
            return "403";
        }

    }


    /**
     * Fonction qui recoit les infos de la tache quotidienne pour ensuite ajouter dans la base de données
     * s'il y a pas eu d'erreur
     * @param daily
     * @param model
     * @return
     */
    @RequestMapping(value = "/newDaily", method = RequestMethod.POST)
    public RedirectView submitTodo(@Valid @ModelAttribute("daily")Daily daily, ModelMap model) {

        String form_daily_name = daily.getName();

        int user_id = daily.getUser_id();

        model.addAttribute("user_id",  user_id);


        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if(form_daily_name != null && !form_daily_name.isEmpty()){

            Daily new_daily = new Daily();
            new_daily.setName(form_daily_name);
            new_daily.setUser_id(user_id);

            dailyRepository.createDaily(new_daily);

            model.addAttribute("message", 2);
            rv.setAttributesMap(model);
            rv.setUrl("/addDaily/{user_id}");
            return rv;


        }else{

            model.addAttribute("message", 1);
            rv.setAttributesMap(model);
            rv.setUrl("/addDaily/{user_id}");
            return rv;
        }

    }

    /**
     * Fonction qui affiche les tâches quotidiennes
     * @param user_id
     * @param model
     * @return
     */
    @RequestMapping(value = "/showDailies/{user_id}", method = RequestMethod.GET)
    public String showDailies(@PathVariable(value = "user_id") Integer user_id,
                              ModelMap model, HttpServletRequest request)
    {
        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);

        if(userIdAuth == user_id) {

            User user = userService.getUser(user_id);

            model.addAttribute("name", user.getUsername());

            model.addAttribute("points", user.getPoints());

            model.addAttribute("level", user.getLevel_number());

            model.addAttribute("user_id", user_id);

            List<Daily> dailies = dailyRepository.getUserDailies(user_id);

            List<Daily> doneDailies = dailyRepository.getDoneUserDailies(user_id);

            model.addAttribute("dailies", dailies);

            model.addAttribute("doneDailies", doneDailies);

            return "showDailies";
        } else {
            return "403";
        }
    }

    /**
     * Fonction qui permet de valider une tâche quotidinne et gagner 5 points
     * Selon le nombre de ses points l'utilisateur peut monter de niveau et gagner de récompenses
     * @param daily_id
     * @param user_id
     * @param daily
     * @param model
     * @return
     */
    @RequestMapping(value = "/checkDaily/{daily_id}/{user_id}", method = RequestMethod.GET)
    public RedirectView checkDaily(@PathVariable(value = "daily_id")  Integer daily_id,
                                   @PathVariable(value = "user_id") Integer user_id,
                                   @ModelAttribute ("daily") Daily daily, ModelMap model,
                                   HttpServletRequest request)
    {

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        int userIdAuth = userService.getUserIdByName(username);

        if (userIdAuth == user_id) {

            User user_before = userService.getUserWithlevel(user_id);
            Integer level_before = user_before.getLevel_number();

            dailyRepository.checkDaily(daily_id);
            boolean updated = userService.updateUserPointsAndLevel(user_id, DAILY_POINTS);

            if (updated) {

                    User user = userService.getUserWithlevel(user_id);
                    Integer new_points = user.getPoints();
                    Integer level = user.getLevel_number();

                if (level_before < level) {

                        Integer level_id = user.getLevel_id();
                /*
                Prize Service
                Vérifier s'il ya des récompenses associés au niveau gagné
                 */

                        Integer prizes = prizeRepository.checkLevelPrizes(level_id, user_id);

                        if (prizes > 0) {
                    /*
                    Gagner un niveau + récompenses
                     */
                            model.addAttribute("user_id", user_id);

                            model.addAttribute("level_id", level_id);

                            rv.setAttributesMap(model);
                            rv.setUrl("/wonPrizesWithLevel/{level_id}/{user_id}");
                            return rv;

                        } else {
                    /*
                    Gagner un niveau sans récompenses
                     */
                            model.addAttribute("message", 5);

                        }
                } else {

                /*
                Gagner juste les points
                 */
                        model.addAttribute("message", 6);
                    }

                    model.addAttribute("user_id", user_id);

                    model.addAttribute("level", level);

                    model.addAttribute("points", new_points);


                    rv.setAttributesMap(model);
                    rv.setUrl("/showDailies/{user_id}");
                    return rv;

            } else {

                    model.addAttribute("user_id", user_id);

                    model.addAttribute("message", 1);

                    rv.setAttributesMap(model);
                    rv.setUrl("/showDailies/{user_id}");
                    return rv;
            }

        } else {

                rv.setAttributesMap(model);
                rv.setUrl("/403");
                return rv;
        }

    }


    /**
     * Fonction qui affiche le formulaire pour la modification de la tâche quotidienne
     * @param daily_id
     * @param user_id
     * @param modified_daily
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyDaily/{daily_id}/{user_id}", method = RequestMethod.GET)
    public String modifyDailyForm(@PathVariable(value = "daily_id")  Integer daily_id,
                                  @PathVariable(value = "user_id") Integer user_id,
                                 @ModelAttribute ("modified_daily") Daily modified_daily,
                                  ModelMap model, HttpServletRequest request)
    {

        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);

        if(userIdAuth == user_id) {

            model.addAttribute("user_id", user_id);

            model.addAttribute("daily_id", daily_id);

            Daily current_daily = dailyRepository.getDaily(daily_id);
            model.addAttribute("current_daily", current_daily);

            return "modifyDaily";
        } else {
            return "403";
        }
    }


    /**
     * Fonction qui permet d'enregistrer la tâche quotidienne modifiee
     * S'il y a pas eu d'erreur
     * @param modified_daily
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifiedDaily", method = RequestMethod.POST)
    public RedirectView submitModifiedDaily(@Valid @ModelAttribute("modified_todo")Daily modified_daily,
                                           ModelMap model) {

        String form_daily_name = modified_daily.getName();

        model.addAttribute("daily_id", modified_daily.getId());

        model.addAttribute("user_id",  modified_daily.getUser_id());

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if(form_daily_name != null && !form_daily_name.isEmpty() ){

            boolean updated = dailyRepository.updateDaily(modified_daily);

            if(updated){

                model.addAttribute("message", 4);
                rv.setAttributesMap(model);
                rv.setUrl("/showDailies/{user_id}");
                return rv;

            }else{

                model.addAttribute("message", 2);
                rv.setAttributesMap(model);
                rv.setUrl("/modifyDaily/{daily_id}/{user_id}");
                return rv;
            }

        }else{

            model.addAttribute("message", 2);
            rv.setAttributesMap(model);
            rv.setUrl("/modifyDaily/{daily_id}/{user_id}");
            return rv;
        }

    }

    /**
     * Fonction qui permet de supprimer une tâche quotidienne
     * @param daily_id
     * @param user_id
     * @param daily
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteDaily/{daily_id}/{user_id}", method = RequestMethod.GET)
    public RedirectView deleteTodo(@PathVariable(value = "daily_id")  Integer daily_id,
                                   @PathVariable(value = "user_id") Integer user_id,
                                   @ModelAttribute ("daily") Daily daily, ModelMap model,
                                   HttpServletRequest request)
    {

        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if(userIdAuth == user_id) {

            boolean deleted_daily = dailyRepository.deleteDaily(daily_id);

            if(deleted_daily){

                model.addAttribute("message", 2);

            }else{

                model.addAttribute("message", 3);

            }

            model.addAttribute("user_id",  user_id);

            rv.setAttributesMap(model);
            rv.setUrl("/showDailies/{user_id}");
            return rv;

        } else {

            rv.setAttributesMap(model);
            rv.setUrl("/403");
            return rv;
        }

    }

}
