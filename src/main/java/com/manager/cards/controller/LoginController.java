package com.manager.cards.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller class for login and related user notification messages.
 */
@Controller
public class LoginController {

    @GetMapping(value = "/")
    public String redirectToLogin() {
        return "forward:/login";
    }

    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "message", required = false) String message,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if(error != null) {
            errorMessage = "Username or Password is incorrect";
        }
        if(logout != null) {
            errorMessage = "Logout successful";
        }
        if (message != null) {
            errorMessage = message;
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
}
