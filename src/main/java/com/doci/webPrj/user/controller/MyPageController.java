package com.doci.webPrj.user.controller;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.service.UploadProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller()
@RequestMapping("/my-page")
public class MyPageController {

    @Autowired
    UploadProfileService uploadProfileService;

    @GetMapping
    public String myPage(Model model, @AuthenticationPrincipal MyUserDetails user) {
        model.addAttribute("user", user);
        String profileImage = user.getProfileImage();
        System.out.println("profileImage = " + profileImage);
        return "user/my-page";
    }

    @PostMapping("/upload/profile")
    @ResponseBody
    public void uploadProfile(@RequestParam("profileImage") MultipartFile file,
                                @AuthenticationPrincipal MyUserDetails user) {

        uploadProfileService.save(file , user);
//        return null;
    }


}
