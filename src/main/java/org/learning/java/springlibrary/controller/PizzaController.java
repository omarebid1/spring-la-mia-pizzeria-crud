package org.learning.java.springlibrary.controller;

import jakarta.validation.Valid;
import org.learning.java.springlibrary.model.Pizza;
import org.learning.java.springlibrary.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepo;


    // TO VIEW ALL PIZZA TYPES
    @GetMapping("/view")
    public String index(Model model){
        List<Pizza> pizzaList = pizzaRepo.findAll();
        model.addAttribute("pizza", pizzaList);
        return "pizza/speciality";
    }

    @GetMapping("/view/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model){
        Optional<Pizza> pizzaOptional = pizzaRepo.findById(id);
        if (pizzaOptional.isPresent()){
            Pizza pizzaFromDB = pizzaOptional.get();
            model.addAttribute("pizza", pizzaFromDB);
            return "pizza/details";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("pizza", new Pizza());
        return "pizza/add";
    }
    @PostMapping("/add")
    public String doAdd(@ModelAttribute("pizza") Pizza pizzaForm, BindingResult bindingResult){
        pizzaRepo.save(pizzaForm);
        return "redirect:/pizza/view";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzaRepo.findById(id);
        if (pizzaOptional.isPresent()) {
            model.addAttribute("pizza", pizzaOptional.get());
            return "pizza/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/pizza/edit";
        }
        pizzaRepo.save(formPizza);
        return "redirect:/pizza/view";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        pizzaRepo.deleteById(id);
        return "redirect:/pizza/view";
    }

    @Service
    public class SearchService {

        @Autowired
        private static PizzaRepository searchRepository;

        public static List<Pizza> search(String query) {
            return searchRepository.findByName(query);
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("query", query);
        return "/pizza/details-details";
    }

    /*

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("query", query);
        return "search-results";
    }
     */
}
