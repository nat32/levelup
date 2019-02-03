package com.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model, String error, String logout) {

        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        return "login";
    }



    @RequestMapping(value="/loginFailed", method= RequestMethod.GET)
    public String loginFailed(ModelMap model) {

        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logout(ModelMap model) {
        return "logout";
    }


    @RequestMapping(value="/403", method= RequestMethod.GET)
    public String error403(ModelMap model){
        return "403";
    }

}
