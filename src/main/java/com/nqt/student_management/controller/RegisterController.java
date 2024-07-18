package com.nqt.student_management.controller;

import com.nqt.student_management.entity.Role;
import com.nqt.student_management.entity.User;
import com.nqt.student_management.repository.RoleRepository;
import com.nqt.student_management.service.UserService;
import com.nqt.student_management.web.RegisterUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegisterController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("registerUser", new RegisterUser());
        return "register";
    }

    @PostMapping("/process")
    public String process(@Valid @ModelAttribute("registerUser") RegisterUser registerUser, BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.findByUsername(registerUser.getUsername()) != null) {
            model.addAttribute("registerUser", registerUser);
            model.addAttribute("existError", "Username is already taken");
            return "register";
        }

        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getName());
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
        user.setEmail(registerUser.getEmail());
        user.setEnabled(true);

        Role defaultRole = roleRepository.findByName("ROLE_USER");
        Collection<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        session.setAttribute("user", user);
        userService.save(user);

        return "confirmation-page";
    }
}
