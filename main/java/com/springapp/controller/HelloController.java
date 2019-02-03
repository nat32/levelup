package com.springapp.controller;

import com.springapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HelloController {

	@Autowired
    UserService userService;
	/**
	 * L'entree de l'application - redirection vers la page de bienvenue
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/")
	public String index(ModelMap model, HttpServletRequest request) {

			Principal principal = request.getUserPrincipal();

			String username = principal.getName();

			int user_id = userService.getUserIdByName(username);

			model.addAttribute("username", username);
			model.addAttribute("user_id", user_id);
			model.addAttribute("message", "Bienvenue au Level Up!");
			return "salut";
	}





}