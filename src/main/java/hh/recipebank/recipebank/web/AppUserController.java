package hh.recipebank.recipebank.web;

import hh.recipebank.recipebank.domain.AppUser;
import hh.recipebank.recipebank.domain.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
public class AppUserController {

    // Fields
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    // Endpoints

    // List users (mapped to /users)
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", appUserRepository.findAll());
        return "userlist";
    }

    // Show add form (prefixed with /users)
    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "adduser";
    }

    // Save user (prefixed path)
    @PostMapping("/users/save")
    public String saveUser(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult, Model model) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (bindingResult.hasErrors()) {
            return "adduser";
        }
        if (user.getUsername() != null && appUserRepository.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "user.username.exists", "Käyttäjänimi on jo käytössä");
            return "adduser";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        appUserRepository.save(user);
        return "redirect:/users";
    }

    // Delete user 
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        if (id == null || !appUserRepository.existsById(id)) {
            throw new NoSuchElementException("Invalid user ID: " + id);
        }
        appUserRepository.deleteById(id);
        return "redirect:/users";
    }

    // Admin list 
    @GetMapping("/admin/users")
    public String adminUserList(Model model) {
        model.addAttribute("users", appUserRepository.findAll());
        return "userlist";
    }

    // Admin add form 
    @GetMapping("/admin/users/add")
    public String addUserFormAdmin(Model model) {
        model.addAttribute("user", new AppUser());
        return "adduser";
    }

    // Admin save 
    @PostMapping("/admin/users/save")
    public String saveUserAdmin(@Valid @ModelAttribute("user") AppUser user, BindingResult bindingResult, Model model) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (bindingResult.hasErrors()) {
            return "adduser";
        }
        if (user.getUsername() != null && appUserRepository.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "user.username.exists", "Käyttäjänimi on jo käytössä");
            return "adduser";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        appUserRepository.save(user);
        return "redirect:/users";
    }
}
