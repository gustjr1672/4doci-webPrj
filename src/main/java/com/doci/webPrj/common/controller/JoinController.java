package com.doci.webPrj.common.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doci.webPrj.common.service.JoinFormService;
import com.doci.webPrj.common.service.MailService;
import com.doci.webPrj.user.entity.Member;


@Controller
@RequestMapping("/join")
public class JoinController {
    @Autowired
    private MailService mailService;
    @Autowired
    private JoinFormService joinFormService;

    private static final Member member = new Member();

    @GetMapping("/email-verification")
    public String mailAuthPage(@Param("error")String error){
        return "/common/email-verification";
    }

    @PostMapping("/email-verification/submit")
    public String mailAuthSubmit(Model model,@RequestParam("email") String email){
        member.setEmail(email);
        if(mailService.isEmailValid(email))
            return "redirect:/join/email-verification?error";
      
        return "redirect:/join/join-form";
    }

    @GetMapping("/join-form")
    public String join(Model model){
        model.addAttribute("newMember",member);
        return "/common/join-form";
    }

    @PostMapping("/join-form")
        public String submit(Model model, @ModelAttribute("member") Member member){
        joinFormService.join(member);
        return "redirect:/join/finish-join";
    }

    @GetMapping("/finish-join")
        public String finishJoin(){
            return "/common/finish-join";
        }

}
