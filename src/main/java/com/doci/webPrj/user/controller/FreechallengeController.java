package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.CategoryService;
import com.doci.webPrj.admin.service.UnitService;
import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.service.FreeChallengeService;

@Controller
@RequestMapping("freeChallenge")
public class FreechallengeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UnitService unitService;
    @Autowired
    FreeChallengeService freeChallengeService;

    @GetMapping("form")
    public String resister(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("unitList", unitList);
        return "user/startchallenge/freechallenge/form";
    }

    @PostMapping("challenge/reg")
    public String challengeReg(FreeChallenge freeChallenge,
            @AuthenticationPrincipal MyUserDetails user) {
        freeChallenge.setMemberId(user.getId());
        freeChallengeService.addChallenge(freeChallenge);
        return "redirect:/main";
    }

}
