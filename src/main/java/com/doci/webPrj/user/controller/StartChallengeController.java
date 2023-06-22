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
import org.springframework.web.servlet.view.RedirectView;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.CategoryService;
import com.doci.webPrj.admin.service.UnitService;
import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.service.StartChallengeService;

@Controller
@RequestMapping("challenge/start")
public class StartChallengeController {

    @Autowired
    StartChallengeService startChallengeService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UnitService unitService;

    @GetMapping("choice/type")
    public String type() {

        return "user/startchallenge/choice/type";
    }

    @GetMapping("freeform")
    public String resister(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("unitList", unitList);
        // model.addAttribute("freeChallenge", freeChallenge);
        return "user/startchallenge/freeform";
    }

    @PostMapping("freechallenge/reg")
    public String freechallengeReg(
            FreeChallenge freeChallenge,
            @AuthenticationPrincipal MyUserDetails user) {

        freeChallenge.setMemberId(user.getId());
        startChallengeService.addFreeChallenge(freeChallenge);

        return "redirect:/main";
    }

    @PostMapping("choice/type/submit")
    public RedirectView submit(@RequestParam("challenge") String type) {

        switch (type) {
            case "individual":
                return new RedirectView("/challenge/start/freeform");

            case "random":
                return new RedirectView("/challenge/start/choice/randomcategory");

            case "group":

                break;
            case "set":

                break;

        }
        return new RedirectView("/startchallenge/choice/type");

    }
}
