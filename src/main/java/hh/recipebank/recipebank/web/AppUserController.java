package hh.recipebank.recipebank.web;

import hh.recipebank.recipebank.domain.AppUser;
import hh.recipebank.recipebank.domain.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    // Lista käyttäjistä
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", appUserRepository.findAll());
        return "userlist";
    }

    // Lisää käyttäjä
    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "adduser";
    }

    // Tallenna käyttäjä
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") AppUser user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        appUserRepository.save(user);
        return "redirect:/users";
    }

    // Poista käyttäjä
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        if (id == null || !appUserRepository.existsById(id)) {
            throw new NoSuchElementException("Invalid user ID: " + id);
        }
        appUserRepository.deleteById(id);
        return "redirect:/users";
    }
}
