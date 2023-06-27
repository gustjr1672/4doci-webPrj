package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.CategoryService;
import com.doci.webPrj.admin.service.UnitService;
import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.entity.Invitation;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.FreeChallengeService;
import com.doci.webPrj.user.service.FriendManageService;
import com.doci.webPrj.user.service.InvitationService;

@Controller
@RequestMapping("freeChallenge")
public class FreechallengeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UnitService unitService;
    @Autowired
    FreeChallengeService freeChallengeService;
    @Autowired
    FriendManageService friendManageService;
    @Autowired
    InvitationService invitationService;

    private static final Invitation invitation = new Invitation();
    
    @GetMapping("freeform")
    public String resister(Model model,@RequestParam("type") String type) {
        List<Category> categoryList = categoryService.findAll();
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("unitList", unitList);
        model.addAttribute("type", type);
        return "user/startchallenge/freechallenge/form";
    }

    @PostMapping("challenge/reg")
    public String challengeReg(
            Model model,
            FreeChallenge freeChallenge,
            @AuthenticationPrincipal MyUserDetails user,
            @ModelAttribute("type") String type,
            RedirectAttributes rttr) {

        freeChallenge.setMemberId(user.getId());
        freeChallengeService.addFreeChallenge(freeChallenge);
        if(type.equals("individual"))
            return "redirect:/main";
        String challengeName = freeChallenge.getName();
        rttr.addFlashAttribute("name",challengeName);
        return "redirect:/freeChallenge/group-invite";
    }

    @GetMapping("group-invite")
    public String groupInvite(Model model,@AuthenticationPrincipal MyUserDetails user){
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        System.out.println(friendList);
        model.addAttribute("friendList", friendList);
        // int challengeId = startChallengeService.getFreechallengeId();
        return "user/startchallenge/groupchallenge/invite";
    }

    @PostMapping("group-invite/reg")
    public String groupRegister(@RequestParam("action") String action,
                                @RequestParam("challengeName") String challengeName,
                                @RequestParam("friend") List<Integer> friends,
                                @AuthenticationPrincipal MyUserDetails user){
        
        int userId = user.getId();
        Integer challengeId = freeChallengeService.getFreechallengeId(challengeName,userId);
        if(action.equals("cancel")){
            freeChallengeService.delete(challengeId);
            return "redirect:/main";
        }
        else{
            invitation.setToMemberId(userId);
            invitation.setFreeChallengeId(challengeId);
            for(int friendId : friends){
             invitation.setFromMemberId(friendId);
           invitationService.invite(invitation);
         }
        return "redirect:/freeChallenge/standby-screen";
        }
    }
        @GetMapping("standby-screen")
        public String standbyScreen(){
            return "user/startchallenge/groupchallenge/standby-screen";
        }

    
}
