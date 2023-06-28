package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.doci.webPrj.user.service.InvitationNotificationService;
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
    @Autowired
    InvitationNotificationService invitationNotificationService;


     private static final Invitation invitation = new Invitation();

    @GetMapping("form")
    public String register(Model model) {
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
                                @RequestParam("friend") List<Integer> friends,
                                @AuthenticationPrincipal MyUserDetails user,
                                RedirectAttributes rttr){
        
        if(action.equals("cancel")){
            groupChallengeService.delete(challengeId);
            return "redirect:/main";
        }
        else{
            invitation.setGroupChallengeId(challengeId);
            for(int friendId : friends){
            invitation.setToMemberId(friendId);
            invitationService.invite(invitation);
            invitationNotificationService.sendRequestNotice(challengeId, friendId);
            GroupChallenge challenge = groupChallengeService.getChallenge(challengeId);
            rttr.addFlashAttribute("challenge",challenge);
         }

        return "redirect:/groupChallenge/standby-screen";
        }
    }
    @GetMapping("standby-screen")
    public String standbyScreen(){
        
        return "user/startchallenge/groupchallenge/standby-screen";
    }

    @GetMapping("standby-screen-member")
    public String standbyScreenMember(){
        
        return "user/startchallenge/groupchallenge/standby-screen-member";
    }

    @GetMapping("invite-request")
    public String groupInvite(Model model, @RequestParam("id") int challengeId){
        System.out.println(challengeId);
        GroupChallenge challenge = groupChallengeService.getChallenge(challengeId);
        System.out.println(challenge);
        Member groupLeader = groupChallengeService.getLeader(challenge.getGroupLeaderId());
        model.addAttribute("challenge",challenge);
        model.addAttribute("leader", groupLeader);
        return "user/startchallenge/groupchallenge/invite-request";
    }

    @PostMapping("invite-request/submit")
    public String groupInvite(@RequestParam("id") int challengeId,
                              @RequestParam("action") String action,
                              @AuthenticationPrincipal MyUserDetails user){
        System.out.println(challengeId);
        if(action.equals("refuse")){
            invitationNotificationService.requestRefuse(user.getId(),challengeId);
            return "redierct:/main";
        }
        invitationNotificationService.requestAccept(user.getId(),challengeId);
        invitationService.requestAccept(user.getId(),challengeId);
        return "redirect:/groupChallenge/standby-screen-member";

    }

    
}
