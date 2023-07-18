package com.doci.webPrj.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.doci.webPrj.user.entity.InvitationMember;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.FriendManageService;
import com.doci.webPrj.user.service.GroupChallengeService;
import com.doci.webPrj.user.service.InvitationService;
import com.doci.webPrj.user.service.PerformanceRecordsService;

import jakarta.servlet.http.HttpSession;

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
    PerformanceRecordsService performanceRecordService;

    @GetMapping("form")
    public String register(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("unitList", unitList);
        return "user/startchallenge/groupchallenge/form";
    }

    @PostMapping("challenge/reg")
    public String challengeReg(GroupChallenge groupChallenge, HttpSession session) {
        System.out.println(groupChallenge.getStartTime());
        session.setAttribute("groupChallenge", groupChallenge);
        return "redirect:/groupChallenge/group-invite";
    }

    @GetMapping("group-invite")
    public String groupInvite(Model model, @AuthenticationPrincipal MyUserDetails user, HttpSession session) {
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        model.addAttribute("friendList", friendList);
        return "user/startchallenge/groupchallenge/invite";
    }

    @PostMapping("group-invite/reg")
    public String groupRegister(HttpSession session,
            @RequestParam("action") String action,
            @RequestParam(name = "friend", required = false) List<Integer> friendList,
            @AuthenticationPrincipal MyUserDetails user,
            RedirectAttributes rttr, Model model) {
        if (action.equals("cancel")) {
            session.removeAttribute("groupChallenge");
            return "redirect:/main";
        } else {
            GroupChallenge groupChallenge = (GroupChallenge) session.getAttribute("groupChallenge");
            groupChallengeService.addChallenge(groupChallenge, user.getId());
            invitationService.invite(friendList, groupChallenge.getId());
            invitationService.inviteLeader(groupChallenge.getId(), user.getId());
            groupChallengeService.groupStart(user.getId(), groupChallenge.getId()); // 방장만 groupstart 에 insert 하는 코드

            return "redirect:/groupChallenge/standby-screen";
        }
    }

    @GetMapping("standby-screen")
    public String standbyScreen(HttpSession session, Model model,
            @AuthenticationPrincipal MyUserDetails user,
            @RequestParam(name = "cid", required = false) Integer groupStartId) {
        GroupChallenge challenge = null;
        int challengeId = 0;

        if (groupStartId != null) {
            challengeId = groupChallengeService.getGroupChallengeIdByGsId(groupStartId);
            challenge = groupChallengeService.getChallenge(challengeId);
        } else {
            challenge = (GroupChallenge) session.getAttribute("groupChallenge");
            challengeId = challenge.getId();

        }
        int userId = challenge.getGroupLeaderId();

        List<InvitationMember> inviList = groupChallengeService.getInviList(challengeId);
        model.addAttribute("challenge", challenge);
        model.addAttribute("inviList", inviList);

        if (userId == user.getId()) { // 방장의 standby 화면으로 가는 코드
            List<Member> friendList = friendManageService.getFriendList(userId); // 방장의 친구목록
            List<Member> notInviList = groupChallengeService.getNotInviList(challengeId, friendList); // 방장의 친구중 초대받지
                                                                                                      // 않은목록
            model.addAttribute("notInviList", notInviList);
            return "user/startchallenge/groupchallenge/standby-screen";
        }
        return "user/startchallenge/groupchallenge/standby-screen-member";

    }

    @GetMapping("invite-request")
    public String groupInvite(Model model, @RequestParam("id") int challengeId) {

        GroupChallenge challenge = groupChallengeService.getChallenge(challengeId);
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("challenge", challenge);
        model.addAttribute("unitList", unitList);
        return "user/startchallenge/groupchallenge/invite-request";
    }

    @PostMapping("invite-request/submit")
    public String groupInvite(HttpSession session, @RequestParam("id") int challengeId,
            @RequestParam("action") String action,
            @AuthenticationPrincipal MyUserDetails user) {

        if (action.equals("refuse")) {
            invitationService.requestRefuse(user.getId(), challengeId);
            groupChallengeService.exit(user.getId(), challengeId);
            return "redirect:/main";
        }
        invitationService.requestAccept(user.getId(), challengeId);
        GroupChallenge groupChallenge = groupChallengeService.getChallenge(challengeId);

        session.setAttribute("groupChallenge", groupChallenge);
        return "redirect:/groupChallenge/standby-screen";
    }

    @PostMapping("start-now")
    public ResponseEntity<String> groupstart(@RequestParam("id") int challengeId) {
        groupChallengeService.groupStartNow(challengeId);
        return ResponseEntity.ok("ok");
    }

}
