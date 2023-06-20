package com.doci.webPrj.admin.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.admin.service.RandomChallengeService;

@RestController("apiRandomChallengeController")
@RequestMapping("admin/api")
public class RandomChallengeController {
    

    @Autowired
    private RandomChallengeService randomChallengeService;

    @GetMapping("randomchallenges")
    public List<RandomChallenge> list(@RequestParam(name ="c", required=false) Integer categoryId){
        System.out.println("아아아ㅏㅇ");
        List<RandomChallenge> list = randomChallengeService.findAllBycategoryId(categoryId);
        System.out.println(list);
        return list;
    }

}
