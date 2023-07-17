package com.doci.webPrj.user.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.service.RandomChoiceService;


@RestController("apiRandomChoiceController")
@RequestMapping("api/randomchoice")
public class RandomChoiceController {

     @Autowired
    RandomChoiceService randomChoiceService;
    @PutMapping("date")
    public Choice updateDate(@RequestBody Map<String, String> requestData){
        randomChoiceService.updateDate(requestData);
        Choice randomChoice = randomChoiceService.getChoice(Integer.parseInt(requestData.get("challengeId")));
        return randomChoice;
    }
}
