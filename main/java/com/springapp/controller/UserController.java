package com.springapp.controller;

import com.springapp.model.Daily;
import com.springapp.model.Prize;
import com.springapp.model.Todo;
import com.springapp.model.User;
import com.springapp.repository.DailyRepository;
import com.springapp.repository.PrizeRepository;
import com.springapp.service.DailyService;
import com.springapp.service.TodoService;
import com.springapp.service.UserService;
import com.springapp.util.ServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Controller
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private DailyService dailyService;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private TodoService todoService;

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(@ModelAttribute ("user") User user, HttpServletRequest request){

        return "createUser";
    }


    /**
     *Fonction qui affiche la page d'erreur s'il ya eu une erreur lors de la connexion utilisateur
     * @param user
     * @return
     */
    @RequestMapping(value = "/connectError", method = RequestMethod.GET)
    public String connectError(@ModelAttribute ("user") User user){

        return "connectError";
    }



    /**
     * Fonction qui affiche profil de l'utilisateur
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/showProfil/{id}", method = RequestMethod.GET)
    public String showProfil(@PathVariable(value = "id") Integer id, ModelMap model, HttpServletRequest request)
    {

        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);


        if(userIdAuth == id) {

            User user = userService.getUserWithlevel(id);

            String name = user.getUsername();

            Integer level =  user.getLevel_number();


            Integer points =   user.getPoints();

            model.addAttribute("id", id);

            model.addAttribute("name", name);

            model.addAttribute("level", level);

            model.addAttribute("points", points);

            return "profil";
        } else {
            return "403";
        }
    }

    /**
     * Fonction qui recois en paramètre les infos utilisateur pour ensuite les enregistrer dans la bd
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    User createUser(@RequestBody User user){

        return userService.createUser(user);
    }

    @RequestMapping(value = "/connectUser", method = RequestMethod.GET)
    public RedirectView connect(ModelMap model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int user_id = userService.getUserIdByName(username);

        User user = userService.getUserWithlevel(user_id);

        RedirectView rv = new RedirectView();

        rv.setContextRelative(true);

        Integer old_level_id = user.getLevel_id();

        Integer points =   user.getPoints();


        Integer level_number = user.getLevel_number();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String dateStringNow = sdf.format(cal.getTime());

        String last_connection = user.getLast_connection();

        if(!last_connection.equals(dateStringNow)){

            int penalty_points = dailyService.checkDailiesAndGetPenalty(user_id);


            if(penalty_points != 0){

                boolean updated = userService.subtractPointsAndUpdateLevel(user_id, penalty_points);

                if(updated){

                    User user_updated = userService.getUserWithlevel(user_id);

                    points = user_updated.getPoints();

                    level_number =  user_updated.getLevel_number();


                    if(old_level_id.equals(user_updated.getLevel_id()))
                    {

                        model.addAttribute("message",  1);

                    }else{
                        model.addAttribute("message",  2);

                    }

                }
            }
        }

        model.addAttribute("level",  level_number);

        model.addAttribute("points",  points);

        userService.updateUserLastConnection(user_id, dateStringNow);

        model.addAttribute("id",  user_id);

        rv.setAttributesMap(model);
        rv.setUrl("/showProfil/{id}");
        return rv;

    }



    /**
     * Fonction qui recoit les infos utilisateur renseignes lors de l'inscription
     * Si les champs sont correctement remplies, l'utilisateur est rédirigé vers son profil
     * Sinon le formualaire ne va pas se renvoyer tant que le nom et le mot de passe seront pas biens remplies
     * @param user
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public RedirectView submit(@ModelAttribute("user") @Valid User user,
                               BindingResult result, ModelMap model) {

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if (result.hasErrors()) {
            rv.setUrl("/register");
            return rv;
        }

        String name = user.getUsername();

        if(userService.ifUserNameExists(name)) {
            model.addAttribute("message", 1);
            rv.setAttributesMap(model);
            rv.setUrl("/register");
            return rv;
        }

        String password = user.getPassword();
        String confirm_password = user.getConfirmPassword();

        if(!password.equals(confirm_password)){
            //Mot de passe doievent être identiques
            model.addAttribute("message", 2);
            rv.setAttributesMap(model);
            rv.setUrl("/register");
            return rv;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        User newuser = new User();
        newuser.setUsername(name);
        newuser.setPassword(encodedPassword);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        String dateStringNow = sdf.format(cal.getTime());

        newuser.setLast_connection(dateStringNow);

        User created_user = userService.createUser(newuser);

        model.addAttribute("message", 1);

        rv.setAttributesMap(model);

        rv.setUrl("/login");
        return rv;
    }



    /**
     * Fonction qui recois une exception et affche le message à l'écran
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public String handle(RuntimeException ex)
    {
        ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());

        return "error";
    }

}