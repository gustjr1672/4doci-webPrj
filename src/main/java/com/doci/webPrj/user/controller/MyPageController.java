package com.doci.webPrj.user.controller;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.service.ChangeProfileService;
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
    ChangeProfileService uploadProfileService;

    @GetMapping
    public String myPage(Model model, @AuthenticationPrincipal MyUserDetails user) {
        model.addAttribute("user", user);
        String profileImage = user.getProfileImage();
        return "user/my-page";
    }

    @PostMapping("/change/new-profile")
    @ResponseBody
    public void newProfile(@RequestParam("profileImage") MultipartFile file,
                                @AuthenticationPrincipal MyUserDetails user) {

        uploadProfileService.save(file , user);
    }

    @PostMapping("/change/normal-profile")
    @ResponseBody
    public void normalProfile(@RequestParam("fileName") String fileName,
                                      @AuthenticationPrincipal MyUserDetails user){
        uploadProfileService.update(fileName,user);
    }


}
