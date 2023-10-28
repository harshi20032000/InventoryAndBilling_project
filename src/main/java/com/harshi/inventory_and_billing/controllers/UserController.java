package com.harshi.inventory_and_billing.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi.inventory_and_billing.entities.User;
import com.harshi.inventory_and_billing.repo.UserRepository;
import com.harshi.inventory_and_billing.service.SecurityService;

/**
 * Controller class for managing user-related operations including registration and login.
 */
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    /**
     * Display the registration page.
     *
     * @return The view name for the registration page.
     */
    @RequestMapping("/showReg")
    public String showRegistrationPage() {
        LOGGER.info("Displaying the registration page");
        return "loginView/registerUser";
    }

    /**
     * Register a new user.
     *
     * @param user The User object to be registered.
     * @return The view name for the login page.
     */
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user) {
        LOGGER.info("Registering user: {}", user);
        user.setPassword((user.getPassword()));  // Ensure proper handling of the password, e.g., hashing.
        userRepository.save(user);
        LOGGER.info("User registered: {}", user);
        return "loginView/login";
    }

    /**
     * Display the login page.
     *
     * @return The view name for the login page.
     */
    @RequestMapping("/showLogin")
    public String showLoginPage() {
        LOGGER.info("Displaying the login page");
        return "loginView/login";
    }

    /**
     * Handle user login.
     *
     * @param email    The user's email for login.
     * @param password The user's password for login.
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The appropriate view based on login success or failure.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                       ModelMap modelMap) {
        LOGGER.info("Attempting login for email: {}", email);
        boolean isLoginSuccess = securityService.login(email, password);
        if (isLoginSuccess) {
            LOGGER.info("Login successful for email: {}", email);
            modelMap.addAttribute("msg", "Login Success!");
            return "dashboardView/landing";
        } else {
            LOGGER.warn("Login failed for email: {}", email);
            modelMap.addAttribute("msg", "Invalid username/password. Try Again");
        }
        return "loginView/login";
    }
}
