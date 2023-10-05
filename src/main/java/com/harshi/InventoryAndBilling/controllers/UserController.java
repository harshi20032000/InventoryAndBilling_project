package com.harshi.InventoryAndBilling.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi.InventoryAndBilling.entities.User;
import com.harshi.InventoryAndBilling.repo.UserRepository;
import com.harshi.InventoryAndBilling.service.SecurityService;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/showReg")
	public String showRegistrationPage() {
		LOGGER.info("Inside showRegistrationPage() on UserController");
		return "loginView/registerUser";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user) {
		LOGGER.info("Inside register() for user " + user+ "on UserController");
		user.setPassword((user.getPassword()));
		userRepository.save(user);
		LOGGER.info("Redirecting to login.html on UserController");
		return "loginView/login";
	}

	@RequestMapping("/showLogin")
	public String showLoginPage() {
		LOGGER.info("Inside to showLoginPage() on UserController");
		return "loginView/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelMap modelMap) {
		LOGGER.info("Inside showLoginPage() with email - " + email+"on UserController");
		boolean isLoginSuccess = securityService.login(email, password);
		if (isLoginSuccess) {
			LOGGER.info("Redirecting landing.html");
			modelMap.addAttribute("msg", "Invalid username/password. Try Again");
			return "dashboardView/landing";
		} else
			modelMap.addAttribute("msg", "Invalid username/password. Try Again");
		LOGGER.info("Redirecting to login.html on UserController");
		return "loginView/login";
	}
}
