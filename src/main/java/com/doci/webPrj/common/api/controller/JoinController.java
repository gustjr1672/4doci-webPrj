package com.doci.webPrj.common.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.common.service.JoinFormService;
import com.doci.webPrj.common.service.MailService;


@RestController(("apiJoinController"))
@RequestMapping("api/join")
public class JoinController {
     @Autowired
    private MailService mailService;
    @Autowired
    private JoinFormService joinFormService;

    @PostMapping("/email-verification")
    public String mailAuth(@RequestParam("email") String email){
        String code = mailService.sendMessage(email);
        return code;
    }

    @GetMapping("/idCheck")
    public boolean idCheck(@RequestParam("id") String userId){
        boolean isIdValid = joinFormService.isIdValid(userId);
        return isIdValid;
    }

    @GetMapping("/nicknameCheck")
    public boolean nicknameCheck(@RequestParam("nic") String nickname){
        boolean isNicknameValid =  joinFormService.isNicknameValid(nickname);
        return isNicknameValid;
    }



}
