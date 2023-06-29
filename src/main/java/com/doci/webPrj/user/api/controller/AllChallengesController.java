package com.doci.webPrj.user.api.controller;

import com.doci.webPrj.user.entity.AllChallenges;
import com.doci.webPrj.user.service.AllChallengesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("apiAllChallengeController")
@RequestMapping("api/all-challenges")
public class AllChallengesController {

    @Autowired
    AllChallengesService allChallengesService;

    @GetMapping("{userId}")
    public List<AllChallenges> list(@PathVariable int userId){
        List<AllChallenges> allChallenges = allChallengesService.findAll(userId);
        return allChallenges;
    }

}
