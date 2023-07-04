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
import com.doci.webPrj.user.entity.InvitationMember;
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
    public String register(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("unitList", unitList);
        return "user/startchallenge/groupchallenge/form";
    }
    @PostMapping("challenge/reg")
    public String challengeReg(GroupChallenge groupChallenge,@AuthenticationPrincipal MyUserDetails user,
                               RedirectAttributes rttr) {

        groupChallengeService.addChallenge(groupChallenge,user.getId());
        rttr.addFlashAttribute("challenge",groupChallenge);
        return "redirect:/groupChallenge/group-invite";
    }

    @GetMapping("group-invite")
    public String groupInvite(Model model,@AuthenticationPrincipal MyUserDetails user){
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        model.addAttribute("friendList", friendList);
        return "user/startchallenge/groupchallenge/invite";
    }

    @PostMapping("group-invite/reg")
    public String groupRegister(@RequestParam("action") String action,
                                @RequestParam("challengeId") int challengeId,
                                @RequestParam(name="friend", required=false) List<Integer> friendList,
                                @AuthenticationPrincipal MyUserDetails user,
                                RedirectAttributes rttr){
        
        if(action.equals("cancel")){
            groupChallengeService.delete(challengeId);
            return "redirect:/main";
        }
        else{
                invitationService.invite(friendList,challengeId);
                GroupChallenge challenge = groupChallengeService.getChallenge(challengeId);
                rttr.addFlashAttribute("challenge",challenge);
                invitationService.inviteLeader(challengeId,user.getId());
                groupChallengeService.groupStart(challengeId,user.getId()); //방장만 groupstart 에 insert 하는 코드
            }

        return "redirect:/groupChallenge/standby-screen";
        
    }
    @GetMapping("standby-screen")
    public String standbyScreen(@ModelAttribute(name="challenge") GroupChallenge challenge,Model model,
                                @AuthenticationPrincipal MyUserDetails user){
        int challengeId = challenge.getId();
        int userId = challenge.getGroupLeaderId();

        List<InvitationMember> inviList = groupChallengeService.getInviList(challengeId);
        model.addAttribute("inviList", inviList);

        if(userId == user.getId()){ // 방장의 standby 화면으로 가는 코드
            List<Member> friendList = friendManageService.getFriendList(userId); //방장의 친구목록
            List<Member> notInviList = groupChallengeService.getNotInviList(challengeId,friendList); //방장의 친구중 초대받지 않은목록
            model.addAttribute("notInviList", notInviList);
            return "user/startchallenge/groupchallenge/standby-screen";
        }
        return "user/startchallenge/groupchallenge/standby-screen-member";
    }


    @GetMapping("invite-request")
    public String groupInvite(Model model, @RequestParam("id") int challengeId){

        GroupChallenge challenge = groupChallengeService.getChallenge(challengeId);
        Member groupLeader = groupChallengeService.getLeader(challenge.getGroupLeaderId());
        model.addAttribute("challenge",challenge);
        model.addAttribute("leader", groupLeader);
        return "user/startchallenge/groupchallenge/invite-request";
    }


    @PostMapping("invite-request/submit")
    public String groupInvite(@RequestParam("id") int challengeId,
                              @RequestParam("action") String action,
                              @AuthenticationPrincipal MyUserDetails user,
                              RedirectAttributes rttr){

        if(action.equals("refuse")){
            invitationService.requestRefuse(user.getId(),challengeId);
            return "redierct:/main";
        }

        invitationService.requestAccept(user.getId(),challengeId);
        GroupChallenge challenge = groupChallengeService.getChallenge(challengeId);
        rttr.addFlashAttribute("challenge",challenge);
        return "redirect:/groupChallenge/standby-screen";

    }

    
}
