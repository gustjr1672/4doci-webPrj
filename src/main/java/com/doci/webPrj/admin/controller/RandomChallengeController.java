package com.doci.webPrj.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.RandomChallengeService;



@Controller
@RequestMapping("/admin/randomchallenge")
public class RandomChallengeController {

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
 
        return "/admin/randomchallenge/register";
    }

    @PostMapping("/register/submit")
    public String submitRegister(RandomChallenge randomChallenge){
        
        randomChallengeService.create(randomChallenge);
        return "redirect:/admin/randomchallenge/manage";
    }

    
    @GetMapping("/edit")
    public String editRandomChallenge(Model model, @RequestParam(name ="c", required=false) Integer categoryId){
        List<Category> challengeCategoryList = randomChallengeService.getCategoryList();
        if(categoryId != null){
        List<RandomChallenge> randomChallengeList = randomChallengeService.findAllBycategoryId(categoryId);
        model.addAttribute("randomChallengeList", randomChallengeList);
        }

        model.addAttribute("challengeCategoryList", challengeCategoryList);
        return "/admin/randomchallenge/edit";
    }

    @PostMapping("/edit/submit")
    public String chioceEdit(Model model, @ModelAttribute("selectedChallenge") int challengeId){

        RandomChallenge selectedChallenge = randomChallengeService.findById(challengeId);
       

        List<Category> challengeCategoryList = randomChallengeService.getCategoryList();
        List<Unit> challengeUnitList = randomChallengeService.getUnitList();

        model.addAttribute("selectedChallenge", selectedChallenge);
        model.addAttribute("challengeCategoryList", challengeCategoryList);
        model.addAttribute("challengeUnitList", challengeUnitList);


        System.out.print(selectedChallenge);
        return "/admin/randomchallenge/edit-detail";
    }

    @PostMapping("/edit/submit/submit")
    public String submitEdit( RandomChallenge randomChallenge){
        randomChallengeService.update(randomChallenge);
        return "redirect:/admin/randomchallenge/manage";
    }


}