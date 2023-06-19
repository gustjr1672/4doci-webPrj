package com.doci.webPrj.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.RandomChallengeService;



@Controller
@RequestMapping("/admin/randomchallenge")
public class RandomChallengeController {

    private static final RandomChallenge randomchallenge = new RandomChallenge();

    @Autowired
    private RandomChallengeService randomChallengeService;

    @GetMapping("/manage")
    public String manage() {
        return "/admin/randomchallenge/manage";
    } 
    
    @GetMapping("/register")
    public String registerRandomChallenge(Model model){
        List<Category> challengeCategoryList = randomChallengeService.getCategoryList();
        List<Unit> challengeUnitList = randomChallengeService.getUnitList();
        model.addAttribute("challengeCategoryList", challengeCategoryList);
        model.addAttribute("challengeUnitList", challengeUnitList);
        model.addAttribute("randomchallenge", randomchallenge);
        return "/admin/randomchallenge/register";
    }

    @PostMapping("/register/submit")
    public String submitRegister(@ModelAttribute("randomChallenge") RandomChallenge randomChallenge){
        
        randomChallengeService.create(randomChallenge);
        return "redirect:/admin/randomchallenge/manage";
    }


}