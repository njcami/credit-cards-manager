package com.manager.cards.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.manager.cards.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.manager.cards.model.CardUser;
import com.manager.cards.service.CardUserService;

import java.util.logging.Logger;

/**
 * Controller class for card users.
 */
@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class.getName());

	@Autowired
	private CardUserService userService;

	@ModelAttribute("user")
	public CardUser formBackingObject() {
		return new CardUser();
	}

	@GetMapping("/users")
	public String userForm(HttpServletRequest request, Model model) {
		if (request.isUserInRole(SecurityConfig.ADMIN_ROLE)) {
			model.addAttribute("users", userService.list());
		}
		return "addUser";
	}

	@PostMapping("/addUser")
	public String saveUser(@ModelAttribute("user") @Valid CardUser cardUser, BindingResult result, Model model,
						   HttpServletRequest request) {

		if (result.hasErrors()) {
			if (request.isUserInRole(SecurityConfig.ADMIN_ROLE)) {
				model.addAttribute("users", userService.list());
			}
			return "addUser";
		}

		userService.save(cardUser);

		try {
			request.login(cardUser.getUsername(), cardUser.getPassword());
		} catch (ServletException e) {
			logger.severe("Error during login after registration:" + e);
		}

		return "redirect:/login?message=Registration+is+successful%2C+please+login";
	}
}
