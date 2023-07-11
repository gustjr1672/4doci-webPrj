package com.doci.webPrj.user.api.controller;

import com.doci.webPrj.config.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiEditPasswordController")
@RequestMapping("api/user")
public class EditPasswordController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/password")
    public boolean isMatchNowPassword(String inputNowPwd ,
                                      @AuthenticationPrincipal MyUserDetails user){
        String password = user.getPassword();

        if (!passwordEncoder.matches(inputNowPwd ,password)) {
            return false;
        }
        return true;
    }
}
