package com.doci.webPrj.common.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doci.webPrj.common.service.MailService;
import com.doci.webPrj.user.entity.Member;


@Controller
@RequestMapping("/join")
public class JoinController {
     @Autowired
    private MailService mailService;

    private static final Member member = new Member();

    @GetMapping("/email-verification")
    public String mailAuthPage(@Param("error")String error){
        System.out.println(error);
        return "/common/email-verification";
    }

    @PostMapping("/email-verification")
    @ResponseBody
    public String mailAuth(@RequestParam("email") String email){
        String code = mailService.sendMessage(email);
        System.out.printf("인증코드 : %s",code);
        return code;
    }

    @PostMapping("/email-verification/submit")
    public String mailAuthSubmit(Model model,@RequestParam("email") String email){
       System.out.printf("이메일은요 %s",email);
       System.out.println(mailService.isEmailValid(email));
        member.setEmail(email);
        if(mailService.isEmailValid(email))
            return "redirect:/join/email-verification?error";
      
        return "redirect:/join/joinForm";
    }
}
