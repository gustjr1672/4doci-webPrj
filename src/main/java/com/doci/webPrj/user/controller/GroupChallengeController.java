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
import com.doci.webPrj.user.entity.GroupChallenge;
import com.doci.webPrj.user.entity.Invitation;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.FriendManageService;
import com.doci.webPrj.user.service.GroupChallengeService;
import com.doci.webPrj.user.service.InvitationService;

@Controller
@RequestMapping("groupChallenge")
public class GroupChallengeController {
    
    @Autowired
    CategoryService categoryService;
    @Autowired
    UnitService unitService;
    @Autowired
    FriendManageService friendManageService;
    @Autowired
    InvitationService invitationService;
    @Autowired
    GroupChallengeService groupChallengeService;

     private static final Invitation invitation = new Invitation();

    @GetMapping("form")
    public String resister(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("unitList", unitList);
        return "user/startchallenge/groupchallenge/form";
    }
    @PostMapping("challenge/reg")
    public String challengeReg(
            GroupChallenge groupChallenge,
            @AuthenticationPrincipal MyUserDetails user,
            RedirectAttributes rttr) {

        groupChallenge.setGroupLeaderId(user.getId());
        groupChallengeService.addChallenge(groupChallenge);
        int challengeId = groupChallenge.getId();

        rttr.addFlashAttribute("id",challengeId);
        return "redirect:/groupChallenge/group-invite";
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
                                @RequestParam("challengeId") int challengeId,
                                @RequestParam("friend") List<Integer> friends){
        
        if(action.equals("cancel")){
            groupChallengeService.delete(challengeId);
            return "redirect:/main";
        }
        else{
            invitation.setGroupChallengeId(challengeId);
            for(int friendId : friends){
             invitation.setToMemberId(friendId);
           invitationService.invite(invitation);
         }
        return "redirect:/groupChallenge/standby-screen";
        }
    }
        @GetMapping("standby-screen")
        public String standbyScreen(){
            return "user/startchallenge/groupchallenge/standby-screen";
        }

}
