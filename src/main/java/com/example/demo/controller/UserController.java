package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SpringUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
//@RequestMapping(value= "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String userPage(ModelMap modelMap,
                           @AuthenticationPrincipal
                                   SpringUser springUser) {
        modelMap.addAttribute("user", springUser.getUser());
        List<User> users=userRepository.findAllUsers(springUser.getUser().getId());
        modelMap.addAttribute("users",users);
        modelMap.addAttribute("sendRequests",userRepository.findAllRequestedUsers(springUser.getUser().getId()));
        return "user";
    }

    @GetMapping("/send")
    public String sendFriendRequest(@AuthenticationPrincipal
            SpringUser userId, @RequestParam("friendId") int friendId) {
         userRepository.addFriendRequest(userId.getUser().getId(),friendId);
         return "redirect:/user";
    }

}
