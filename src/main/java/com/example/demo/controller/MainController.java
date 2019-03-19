package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SpringUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }



    @GetMapping("/admin")
    public String adminPage(ModelMap modelMap,
                            @AuthenticationPrincipal
                                    SpringUser springUser) {
        modelMap.addAttribute("user", springUser.getUser());
        return "admin";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal
                                       SpringUser springUser) {
        if (springUser.getUser().getUserType() == UserType.ADMIN) {
            return "redirect:/admin";
        }
        return "redirect:/user";

    }


}
