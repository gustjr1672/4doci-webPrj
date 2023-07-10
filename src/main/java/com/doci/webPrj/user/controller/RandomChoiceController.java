package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.ChoiceRandomList;
import com.doci.webPrj.admin.service.CategoryService;
import com.doci.webPrj.admin.service.UnitService;
import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.service.RandomChoiceService;

@Controller
@RequestMapping("randomChallenge")
public class RandomChoiceController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UnitService unitService;
    @Autowired
    RandomChoiceService randomChoiceService;

    @GetMapping("choice/category")
    public String randomCategory(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "user/startchallenge/randomchallenge/choice-category";
    }

    @PostMapping("choice/challenge")
    public String randomCategorySubmit(Model model, @RequestParam("options") String[] categoryIdList) {

        List<ChoiceRandomList> list = randomChoiceService.getRandomList(categoryIdList);
        model.addAttribute("randomList", list);
        model.addAttribute("choice", new Choice());
        return "user/startchallenge/randomchallenge/choice-challenge";
    }

    @PostMapping("randomchallenge/reg")
    public String randomChallengeReg(Choice choice,
            @AuthenticationPrincipal MyUserDetails user) {
        choice.setMemberId(user.getId());
        System.out.println(choice);
        randomChoiceService.addRandomChallenge(choice);
        return "redirect:/main";

    }
}
