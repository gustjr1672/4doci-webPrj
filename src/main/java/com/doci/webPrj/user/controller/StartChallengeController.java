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
import org.springframework.web.servlet.view.RedirectView;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.RandomChallenge;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.CategoryService;
import com.doci.webPrj.admin.service.UnitService;
import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.Choice;
import com.doci.webPrj.user.entity.FreeChallenge;
import com.doci.webPrj.user.entity.Invitation;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.InvitationRepository;
import com.doci.webPrj.user.service.FriendManageService;
import com.doci.webPrj.user.service.InvitationService;
import com.doci.webPrj.user.service.StartChallengeService;

@Controller
@RequestMapping("challenge/start")
public class StartChallengeController {

    @Autowired
    StartChallengeService startChallengeService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UnitService unitService;
    @Autowired
    FriendManageService friendManageService;
    @Autowired
    InvitationService invitationService;
    
    private static final Invitation invitation = new Invitation();

    @GetMapping("choice/type")
    public String type() {

        return "user/startchallenge/choice/type";
    }

    @GetMapping("freeform")
    public String resister(Model model,@RequestParam("type") String type) {
        List<Category> categoryList = categoryService.findAll();
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("unitList", unitList);
        model.addAttribute("type", type);
        return "user/startchallenge/freeform";
    }

    @PostMapping("freechallenge/reg")
    public String freechallengeReg(
            Model model,
            FreeChallenge freeChallenge,
            @AuthenticationPrincipal MyUserDetails user,
            @ModelAttribute("type") String type,
            RedirectAttributes rttr) {

        freeChallenge.setMemberId(user.getId());
        startChallengeService.addFreeChallenge(freeChallenge);
        
        if(type.equals("individual"))
            return "redirect:/main";
        String challengeName = freeChallenge.getName();
        rttr.addFlashAttribute("name",challengeName);
        return "redirect:/challenge/start/group-invite";
    }

    @PostMapping("randomchallenge/reg")
    public String randomChallengeReg(Choice choice,
            @AuthenticationPrincipal MyUserDetails user) {
        choice.setMemberId(user.getId());
        startChallengeService.addRandomChallenge(choice);
        return "redirect:/main";

    }

    @PostMapping("choice/type/submit")
    public String submit(@RequestParam("challenge") String type) {

        switch (type) {
            case "individual":
                return "redirect:/challenge/start/freeform?type="+type;
            case "random":
                return "redirect:/challenge/start/choice/randomcategory";
            case "group":
                 return "redirect:/challenge/start/freeform?type="+type;
            case "set":

                break;

        }
        return "redirect:/startchallenge/choice/type";

    }

    @GetMapping("choice/randomcategory")
    public String randomCategory(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "user/startchallenge/choice/randomcategory";
    }

    @PostMapping("choice/randomchallenge")
    public String randomCategorySubmit(Model model, @RequestParam("options") String[] categoryIdList) {

        List<RandomChallenge> list = startChallengeService.getRandomList(categoryIdList);
        model.addAttribute("randomList", list);
        model.addAttribute("choice", new Choice());
        return "user/startchallenge/choice/randomchallenge";
    }

    @GetMapping("group-invite")
    public String groupInvite(Model model,@AuthenticationPrincipal MyUserDetails user){
        List<Member> friendList = friendManageService.getFriendList(user.getId());
        System.out.println(friendList);
        model.addAttribute("friendList", friendList);
        // int challengeId = startChallengeService.getFreechallengeId();
        return "user/startchallenge/group-invite";
    }

    @PostMapping("group-invite/reg")
    public String groupRegister(@RequestParam("action") String action,
                                @RequestParam("challengeName") String challengeName,
                                @RequestParam("friend") List<Integer> friends,
                                @AuthenticationPrincipal MyUserDetails user){
        
        int userId = user.getId();
        Integer challengeId = startChallengeService.getFreechallengeId(challengeName,userId);
        if(action.equals("cancel")){
            startChallengeService.delete(challengeId);
            return "redirect:/main";
        }
        else{
            invitation.setToMemberId(userId);
            invitation.setFreeChallengeId(challengeId);
            for(int friendId : friends){
             invitation.setFromMemberId(friendId);
           invitationService.invite(invitation);
         }
        return "redirect:/challenge/start/standby-screen";
        }
    }
        @GetMapping("standby-screen")
        public String standbyScreen(){
            return "user/startchallenge/standby_screen";
        }

    

}
