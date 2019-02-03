package com.springapp.controller;

import com.springapp.model.Prize;
import com.springapp.model.User;
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
public class PrizeController {

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private UserService userService;


    /**
     * Fonction qui affiche le formulaire pour l'ajout d'une récompense
     * @param user_id
     * @param prize
     * @param model
     * @return
     */
    @RequestMapping(value = "/addPrize/{user_id}", method = RequestMethod.GET)
    public String addPrize(@PathVariable(value = "user_id") Integer user_id,
                           @ModelAttribute ("prize") Prize prize, ModelMap model,
                           HttpServletRequest request)
    {
        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);


        if(userIdAuth == user_id) {
            model.addAttribute("user_id", user_id);
            return "addPrize";
        } else {
            return "403";
        }

    }

    /**
     * Fonction qui recoit les infos récompense et les enregistre si elles sont correctes
     * @param prize
     * @param model
     * @return
     */
    @RequestMapping(value = "/newPrize", method = RequestMethod.POST)
    public RedirectView submitPrize(@ModelAttribute("prize") @Valid Prize prize , ModelMap model) {


        String form_prize_name = prize.getName();

        Integer form_level_id = prize.getLevel_id();

        Integer user_id = prize.getUser_id();

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if(form_prize_name != null && !form_prize_name.isEmpty() && (form_level_id != 0)){

            Prize new_prize = new Prize();
            new_prize.setName(form_prize_name);
            new_prize.setLevel_id(form_level_id);
            new_prize.setUser_id(user_id);

            prizeRepository.createPrize(new_prize);

            model.addAttribute("user_id",  user_id);
            model.addAttribute("message", 2);

            rv.setAttributesMap(model);
            rv.setUrl("/addPrize/{user_id}");
            return rv;


        }else{

            model.addAttribute("message", 1);
            model.addAttribute("user_id",user_id);

            rv.setAttributesMap(model);
            rv.setUrl("/addPrize/{user_id}");
            return rv;
        }

    }

    /**
     * Fonction qui affiche la liste de récompenes ainsi que les niveaux qu'il faut atteindre pour les déblocker
     * @param user_id
     * @param model
     * @return
     */
    @RequestMapping(value = "/showPrizes/{user_id}", method = RequestMethod.GET)
    public String showPrizes(@PathVariable(value = "user_id") Integer user_id, ModelMap model,
                             HttpServletRequest request)
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
            List<Prize> prizes = prizeRepository.getUserPrizes(user_id);
            model.addAttribute("prizes", prizes);
            return "showPrizes";
        } else {
            return "403";
        }
    }

    /**
     * Fonction qui affiche la liste de récompenses gagnées par l'utilisateur
     * @param user_id
     * @param model
     * @return
     */
    @RequestMapping(value = "/wonPrizes/{user_id}", method = RequestMethod.GET)
    public String wonPrizes(@PathVariable(value = "user_id") Integer user_id, ModelMap model,
                            HttpServletRequest request)
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

            List<Prize> prizes = prizeRepository.getUserWonPrizes(user_id);

            model.addAttribute("prizes", prizes);
            return "wonPrizes";

        } else {
            return "403";
        }
    }

    /**
     * Fonction qui affiche les réompenses gagnées par l'utilisateur lorsqu'il en gange en montant de niveau
     * @param level_id
     * @param user_id
     * @param model
     * @return
     */
    @RequestMapping(value = "/wonPrizesWithLevel/{level_id}/{user_id}", method = RequestMethod.GET)
    public String wonPrizesWithLevel(@PathVariable(value = "level_id")  Integer level_id,
                                     @PathVariable(value = "user_id") Integer user_id, ModelMap model,
                                     HttpServletRequest request)
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

            List<Prize> prizes = prizeRepository.getWonPrizes(level_id, user_id);

        /*
        Changer le statut des récompenes à "gagné"
         */
            prizeRepository.updateWonPrizes(prizes);

            model.addAttribute("prizes", prizes);

            return "wonPrizesWithLevel";
        } else {
            return "403";
        }
    }

    /**
     * Fonction qui affiche le formulaire permettant de modifier une récompense
     * @param prize_id
     * @param user_id
     * @param modified_prize
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyPrize/{prize_id}/{user_id}", method = RequestMethod.GET)
    public String modifyPrizeForm(@PathVariable(value = "prize_id")  Integer prize_id,
                                  @PathVariable(value = "user_id") Integer user_id,
                                  @ModelAttribute ("modified_prize") Prize modified_prize, ModelMap model,
                                  HttpServletRequest request)
    {
        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);

        if(userIdAuth == user_id) {

            model.addAttribute("user_id", user_id);

            model.addAttribute("prize_id", prize_id);


            Prize current_prize = prizeRepository.getPrizeWithLevel(prize_id);
            model.addAttribute("current_prize", current_prize);
            return "modifyPrize";
        } else {
            return "403";
        }
    }

    /**
     * Fonction qui recoit les infos d'une récompense modifiee et la mets à jour
     * S'il n'y a pas eu d'erreur
     * @param modified_prize
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifiedPrize", method = RequestMethod.POST)
    public RedirectView submitModifiedPrize(@ModelAttribute("modified_prize")Prize modified_prize,
                                        ModelMap model) {

        String form_prize_name = modified_prize.getName();

        Integer form_level_id = modified_prize.getUser_id();

        model.addAttribute("prize_id", modified_prize.getId());

        model.addAttribute("user_id", modified_prize.getUser_id());


        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if (form_prize_name != null && !form_prize_name.isEmpty() && (form_level_id != 0)) {

            boolean updated = prizeRepository.updatePrize(modified_prize);

            if (updated) {

                model.addAttribute("message", 4);

                rv.setAttributesMap(model);
                rv.setUrl("/showPrizes/{user_id}");
                return rv;

            } else {

                model.addAttribute("message", 2);

                rv.setAttributesMap(model);
                rv.setUrl("/modifyPrize/{prize_id}/{user_id}");
                return rv;
            }


        } else {

            model.addAttribute("message", 1);

            rv.setAttributesMap(model);
            rv.setUrl("/modifyPrize/{prize_id}/{user_id}");
            return rv;
        }

    }

    /**
     * Fonction permettant supprimer une récompense
     * @param prize_id
     * @param user_id
     * @param from
     * @param prize
     * @param model
     * @return
     */
    @RequestMapping(value = "/deletePrize/{prize_id}/{user_id}/{from}", method = RequestMethod.GET)
    public RedirectView deletePrize(@PathVariable(value = "prize_id")  Integer prize_id,
                                    @PathVariable(value = "user_id") Integer user_id,
                                    @PathVariable(value = "from") String from,@ModelAttribute ("prize") Prize prize,
                                    ModelMap model, HttpServletRequest request)
    {
        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if(userIdAuth == user_id) {

            boolean deleted_prize = prizeRepository.deletePrize(prize_id);

            if(deleted_prize){

                model.addAttribute("message", 2);

            }else{

                model.addAttribute("message", 3);

            }

            model.addAttribute("user_id",  user_id);

            model.addAttribute("from",  from);


            rv.setAttributesMap(model);
            rv.setUrl("/{from}/{user_id}");
            return rv;

        } else {

            rv.setAttributesMap(model);
            rv.setUrl("/403");
            return rv;
        }

    }

}
