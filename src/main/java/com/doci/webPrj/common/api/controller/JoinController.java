package com.doci.webPrj.common.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.common.service.MailService;


@RestController(("apiJoinController"))
@RequestMapping("api/join")
public class JoinController {
     @Autowired
    private MailService mailService;

    @PostMapping("/email-verification")
    public String mailAuth(@RequestParam("email") String email){
        System.out.printf("이메일은요 %s",email);
        String code = mailService.sendMessage(email);
        System.out.printf("인증코드 : %s",code); // commit 전 삭제
        return code;
    }


}
