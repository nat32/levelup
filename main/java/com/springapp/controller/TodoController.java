package com.springapp.controller;

import com.springapp.model.Difficulty;
import com.springapp.model.Todo;
import com.springapp.model.User;
import com.springapp.repository.PrizeRepository;
import com.springapp.service.TodoService;
import com.springapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @Autowired
    private PrizeRepository prizeRepository;

    /**
     * Fonction permettant d'enregistrer une nouvelle tâche à faire dans la base de données
     * @param todo
     * @return
     */
    @RequestMapping(value = "/registerTodo", method = RequestMethod.POST)
    public @ResponseBody
    Todo createTodo(@RequestBody Todo todo){

        return todoService.createTodo(todo);
    }

    /**
     * Fonction permettant de valider une tâche à faire et gagner des points
     * Selon le nombre de points gagnes l'utilisateur peut monter de niveau et gagner de récompenses
     * @param todo_id
     * @param user_id
     * @param todo
     * @param model
     * @return
     */
    @RequestMapping(value = "/checkTodo/{todo_id}/{user_id}", method = RequestMethod.GET)
    public RedirectView checkTodo(@PathVariable(value = "todo_id")  Integer todo_id,
                                  @PathVariable(value = "user_id") Integer user_id,
                                  @ModelAttribute ("todo") Todo todo, ModelMap model,
                                  HttpServletRequest request)
    {

        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if(userIdAuth == user_id) {

            User user_before = userService.getUserWithlevel(user_id);

            int level_before = user_before.getLevel_number();

            int  gained_points = todoService.checkTodoAndGetPoints(todo_id);

            boolean updated = userService.updateUserPointsAndLevel(user_id , gained_points);

            if(updated){

                User user = userService.getUserWithlevel(user_id);

                int new_points = user.getPoints();

                int level = user.getLevel_number();

                if(level_before < level){

                    Integer level_id = user.getLevel_id();

                    /*
                    Prize Service
                    Vérifier s'il ya des récompenses associés au niveau gagné
                     */
                    Integer prizes = prizeRepository.checkLevelPrizes(level_id, user_id);

                    if(prizes > 0 ){

                    /*
                    Gagner un niveau + récompenses
                     */
                        model.addAttribute("user_id",  user_id);
                        model.addAttribute("level_id",  level_id);
                        rv.setAttributesMap(model);
                        rv.setUrl("/wonPrizesWithLevel/{level_id}/{user_id}");
                        return rv;


                    }else{
                    /*
                    Gagner un niveau sans récompense
                     */
                        model.addAttribute("message",  4);

                    }

                }else{
                    model.addAttribute("message",  1);
                }

                model.addAttribute("user_id",  user_id);

                model.addAttribute("level",  level);

                model.addAttribute("points",  new_points);

                rv.setAttributesMap(model);
                rv.setUrl("/doneTodos/{user_id}");
                return rv;

            }else{

                model.addAttribute("user_id",  user_id);
                model.addAttribute("message", 1);

                rv.setAttributesMap(model);
                rv.setUrl("/showTodos/{user_id}");
                return rv;
            }

        } else {

            rv.setAttributesMap(model);
            rv.setUrl("/403");
            return rv;
        }




    }

    /**
     * Fonction qui affiche le formulaire permettant de modifier une tâche à faire
     * @param todo_id
     * @param user_id
     * @param modified_todo
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyTodo/{todo_id}/{user_id}", method = RequestMethod.GET)
    public String modifyTodoForm(@PathVariable(value = "todo_id")  Integer todo_id,
                                 @PathVariable(value = "user_id") Integer user_id,
                                 @ModelAttribute ("modified_todo") Todo modified_todo,
                                 ModelMap model, HttpServletRequest request)
    {
        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);


        if(userIdAuth == user_id) {

            model.addAttribute("user_id", user_id);

            model.addAttribute("todo_id", todo_id);


            Todo current_todo = todoService.getTodoWithDifficulty(todo_id);
            model.addAttribute("current_todo", current_todo);
            return "modifyTodo";
        } else {
            return "403";
        }
    }

    /**
     * Fonction qui enregistre les infos de la tache modifiée
     * S'il y a pas eu d'erreur
     * @param modified_todo
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifiedTodo", method = RequestMethod.POST)
    public RedirectView submitModifiedTodo(@Valid @ModelAttribute("modified_todo")Todo modified_todo,
                                   BindingResult result, ModelMap model) {

        String form_todo_name = modified_todo.getName();

        Integer form_difficulty_id = modified_todo.getDifficulty_id();

        model.addAttribute("todo_id", modified_todo.getId());

        model.addAttribute("user_id",  modified_todo.getUser_id());


        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);

        if(form_todo_name != null && !form_todo_name.isEmpty() && (form_difficulty_id != 0)){

            boolean updated = todoService.updateTodo(modified_todo);

            if(updated){

                model.addAttribute("message", 4);

                rv.setAttributesMap(model);
                rv.setUrl("/showTodos/{user_id}");
                return rv;

            }else{

                model.addAttribute("message", 2);

                rv.setAttributesMap(model);
                rv.setUrl("/modifyTodo/{todo_id}/{user_id}");
                return rv;
            }


        }else{

            model.addAttribute("message", 1);

            rv.setAttributesMap(model);
            rv.setUrl("/modifyTodo/{todo_id}/{user_id}");
            return rv;
        }



    }


    /**
     * Fonction permettant supprimer une tache a faire
     * @param todo_id
     * @param user_id
     * @param from
     * @param todo
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteTodo/{todo_id}/{user_id}/{from}", method = RequestMethod.GET)
    public RedirectView deleteTodo(@PathVariable(value = "todo_id")  Integer todo_id,
                                   @PathVariable(value = "user_id") Integer user_id,
                                   @PathVariable(value = "from") String from,
                                   @ModelAttribute ("todo") Todo todo, ModelMap model,
                                   HttpServletRequest request)
    {

        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);


        if(userIdAuth == user_id) {

            boolean deleted_todo = todoService.deleteTodo(todo_id);

            if(deleted_todo){

                model.addAttribute("message", 2);

            }else{

                model.addAttribute("message", 3);

            }

            model.addAttribute("user_id",  user_id);

            model.addAttribute("from",  from);

            RedirectView rv = new RedirectView();
            rv.setContextRelative(true);
            rv.setAttributesMap(model);
            rv.setUrl("/{from}/{user_id}");
            return rv;

        } else {
            RedirectView rv = new RedirectView();
            rv.setContextRelative(true);
            rv.setAttributesMap(model);
            rv.setUrl("/403");
            return rv;
        }
    }

    /**
     * Fonction qui affiche le formulaire permettant d'ajouter une tache a faire
     * @param user_id
     * @param todo
     * @param model
     * @return
     */
    @RequestMapping(value = "/addTodo/{user_id}", method = RequestMethod.GET)
    public String addTodo(@PathVariable(value = "user_id") Integer user_id, @ModelAttribute ("todo") Todo todo,
                          ModelMap model, HttpServletRequest request)
    {
        Principal principal = request.getUserPrincipal();

        String username = principal.getName();

        int userIdAuth = userService.getUserIdByName(username);


        if(userIdAuth == user_id) {
            model.addAttribute("user_id", user_id);

            return "addTodo";
        } else {
            return "403";
        }
    }

    /**
     * Fonction permettant ajouter une nouvelle tache a faire
     * @param todo
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/newTodo", method = RequestMethod.POST)
    public RedirectView submitTodo(@ModelAttribute("todo") @Valid Todo todo,
                               BindingResult result, ModelMap model) {


        String form_todo_name = todo.getName();

        Integer form_difficulty_id = todo.getDifficulty_id();

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        if(form_todo_name != null && !form_todo_name.isEmpty() && (form_difficulty_id != 0)){

            int user_id = todo.getUser_id();


            Todo new_todo = new Todo();
            new_todo.setName(form_todo_name);
            new_todo.setDifficulty_id(todo.getDifficulty_id());
            new_todo.setUser_id(user_id);

            todoService.createTodo(new_todo);

            model.addAttribute("user_id",  user_id);

            model.addAttribute("message", 2);

            rv.setAttributesMap(model);
            rv.setUrl("/addTodo/{user_id}");
            return rv;


        }else{

            model.addAttribute("message", 1);
            model.addAttribute("user_id", todo.getUser_id());

            rv.setAttributesMap(model);
            rv.setUrl("/addTodo/{user_id}");
            return rv;
        }



    }

    /**
     * Fonction qui retourne la liste de 3 niveaux de difficulté notamment pour l'ajout d'une tache à faire
     * @return
     */
    @RequestMapping(value = "/difficulties", method = RequestMethod.GET)
    public @ResponseBody List<Difficulty> findAllDifficulties() {


        return todoService.findAllDifficulties();
    }

    /**
     * Fonction qui affiche les tâches pas encore accomplies
     * @param user_id
     * @param model
     * @return
     */
    @RequestMapping(value = "/showTodos/{user_id}", method = RequestMethod.GET)
    public String showTodos(@PathVariable(value = "user_id") Integer user_id,
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

            List<Todo> todos = todoService.getUserTodos(user_id);
            model.addAttribute("todos", todos);
            return "showTodos";

        } else {
            return "403";
        }
    }

    /**
     * Fonction qui affiche les tâches accomplies
     * @param user_id
     * @param model
     * @return
     */
    @RequestMapping(value = "/doneTodos/{user_id}", method = RequestMethod.GET)
    public String showDoneTodos(@PathVariable(value = "user_id") Integer user_id,
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
            List<Todo> todos = todoService.getUserDoneTodos(user_id);
            model.addAttribute("todos", todos);
            return "doneTodos";
        } else {
            return "403";
        }
    }

    /**
     * Fonction utilisé pour le test qui affiche la liste de tâches à faire
     * @return
     */
    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public @ResponseBody List<Todo> getTodos() {
        return todoService.getTodos();
    }


}
