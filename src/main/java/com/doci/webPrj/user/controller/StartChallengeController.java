package com.doci.webPrj.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("challenge/start")
public class StartChallengeController {

    @GetMapping("choice/type")
    public String type() {

        return "user/startchallenge/choice/type";
    }


    @PostMapping("choice/type/submit")
    public String submit(@RequestParam("challenge") String type) {

        switch (type) {
            case "individual":
                return "redirect:/freeChallenge/freeform?type="+type;
            case "random":
                return "redirect:/randomChallenge/choice/randomcategory";
            case "group":
                 return "redirect:/freeChallenge/freeform?type="+type;
            case "set":

                break;

        }
        return "redirect:/challenge/start/choice/type";

    }

    

    

}
