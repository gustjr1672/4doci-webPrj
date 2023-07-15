package com.doci.webPrj.user.controller;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.PastChallengeView;
import com.doci.webPrj.user.service.ChangeProfileService;
import com.doci.webPrj.user.service.MypageService;

import java.util.List;

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
    @Autowired
    MypageService service;

    @GetMapping
    public String myPage(Model model, @AuthenticationPrincipal MyUserDetails user) {
        String profileImage = user.getProfileImage();
        List<PastChallengeView> pastList = service.getPastChallenge(user.getId());
        int totalChallenge = pastList.size();
        int successChallenge = 0;
        for (PastChallengeView pastChallenge : pastList) {
            if (pastChallenge.getResult().equals("성공"))
                successChallenge++;
        }
        int failChallenge = totalChallenge - successChallenge;
        model.addAttribute("totalChallenge", totalChallenge);
        model.addAttribute("successChallenge", successChallenge);
        model.addAttribute("failChallenge", failChallenge);
        model.addAttribute("user", user);

        return "user/my-page";
    }

    @PostMapping("/change/new-profile")
    @ResponseBody
    public void newProfile(@RequestParam("profileImage") MultipartFile file,
            @AuthenticationPrincipal MyUserDetails user) {

        uploadProfileService.save(file, user);
    }

    @PostMapping("/change/normal-profile")
    @ResponseBody
    public void normalProfile(@RequestParam("fileName") String fileName,
            @AuthenticationPrincipal MyUserDetails user) {
        uploadProfileService.update(fileName, user);
    }

}
