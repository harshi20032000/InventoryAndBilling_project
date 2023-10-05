package com.harshi.InventoryAndBilling.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.harshi.InventoryAndBilling.entities.Reps;
import com.harshi.InventoryAndBilling.service.RepsService;

@Controller
public class RepsController {

    @Autowired
    private RepsService repsService;

    @ModelAttribute("reps")
    public Reps getReps() {
        return new Reps();
    }

    @RequestMapping("/showRepsList")
    public String showRepsList(ModelMap modelMap) {
        List<Reps> repsList = repsService.showRepsList();
        modelMap.addAttribute("repsList", repsList);
        return "repsView/repsList";
    }

    @RequestMapping("/showAddReps")
    public String showAddReps(Model model) {
        return "repsView/addReps";
    }

    @RequestMapping("/addReps")
    public String addReps(@ModelAttribute("reps") Reps reps, ModelMap modelMap) {
        Reps savedReps = repsService.saveReps(reps);
        List<Reps> repsList = repsService.showRepsList();
        modelMap.addAttribute("repsList", repsList);
        String msg = "Representative saved with ID - " + savedReps.getRepId();
        modelMap.addAttribute("msg", msg);
        return "repsView/repsList";
    }

    @GetMapping("/repsDetails/{repId}")
    public String showRepsDetails(@PathVariable Long repId, Model model) {
        Reps reps = repsService.getRepsById(repId);
        model.addAttribute("reps", reps);
        return "repsView/repsDetails";
    }
}
