package com.springapp.controller;

import com.springapp.model.Level;
import com.springapp.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LevelController {

	@Autowired
	private LevelRepository levelRepository;

	/**
	 * Fonction qui a été utilisée lors de la création de 100 niveaux disponibles
	 * @param level
	 * @return
	 */
	/*
	@RequestMapping(value = "/createLevel", method = RequestMethod.POST)
	public @ResponseBody Level createLevel(@RequestBody Level level) {
		return levelRepository.createLevel(level);
	}
	*/

	/**
	 * Fonction qui retournes les niveaux disponibles notamment pour l'ajout d'une recompense
	 * @return
	 */
	@RequestMapping(value = "/levels", method = RequestMethod.GET)
	public @ResponseBody List<Level> findAllLevels() {

		return levelRepository.findAllLevels();
	}

}