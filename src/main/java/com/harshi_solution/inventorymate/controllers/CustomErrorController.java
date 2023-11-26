package com.harshi_solution.inventorymate.controllers;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi_solution.inventorymate.util.Constants;

@Controller
public class CustomErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(Model model) {
        // Retrieve error information
        // You can add more attributes to the model as needed
        model.addAttribute(Constants.ERROR, errorAttributes.getError(null));

        return Constants.ERROR;
    }

    public String getErrorPath() {
        return "/error";
    }
}
