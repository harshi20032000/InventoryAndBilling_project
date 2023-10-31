package com.harshi.inventory_and_billing.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.harshi.inventory_and_billing.entities.User;
import com.harshi.inventory_and_billing.service.UserService;

/**
 * Controller class for managing user-related operations including registration and login.
 */
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

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
     * @param modelMap ModelMap for adding attributes to the view.
     * @return The view name for the login page or the error view in case of an exception.
     */
    @PostMapping(value = "/registerUser")
    public String register(@ModelAttribute("user") User user, ModelMap modelMap) {
        LOGGER.info("Registering user: {}", user);
        try {
            User savedUser = userService.saveUser(user);
            LOGGER.info("User registered: {}", savedUser);
            return "loginView/login";
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); // You can log the exception for debugging purposes

            // Add an error message to the model to display to the user
            modelMap.addAttribute("error", "An error occurred while registering the user. Please try again.");
            modelMap.addAttribute("errorMessage", e.getMessage());
            return "error"; // Return to the error view with the error message
        }
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
    @PostMapping(value = "/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                       ModelMap modelMap) {
        LOGGER.info("Attempting login for email: {}", email);
        email=email.toUpperCase();
        password=password.toUpperCase();
        boolean isLoginSuccess = userService.login(email, password);
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
