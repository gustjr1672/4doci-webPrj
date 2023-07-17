package com.doci.webPrj.user.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.service.FreeChallengeService;


@RestController("apiFreeChallengeController")
@RequestMapping("api/freechallenge")
public class FreechallengeController {

     @Autowired
    FreeChallengeService freeChallengeService;
    @PutMapping("date")
    public FreeChallenge updateDate(@RequestBody Map<String, String> requestData){
        freeChallengeService.updateDate(requestData);
        FreeChallenge challenge = freeChallengeService.getChallenge(Integer.parseInt(requestData.get("challengeId")));
        return challenge;
    }
}
