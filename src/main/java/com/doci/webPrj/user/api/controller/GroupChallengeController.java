package com.doci.webPrj.user.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.entity.InvitationMember;
import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.service.FriendManageService;
import com.doci.webPrj.user.service.GroupChallengeService;
import com.doci.webPrj.user.service.InvitationService;

@RestController("apiGroupChallengeController")
@RequestMapping("api/groupchallenge")
public class GroupChallengeController {
    
    @Autowired
    GroupChallengeService groupChallengeService;
    @Autowired
    InvitationService invitationService;
    @Autowired
    FriendManageService friendManageService;


   @PutMapping
   public void add(@RequestParam("friendList") List<Integer> friendList,
                                     @RequestParam("challengeId") Integer challengeId){
       invitationService.invite(friendList,challengeId);
   }

   @GetMapping("{challengeId}")
   public ResponseEntity<Map<String, Object>> list(@PathVariable("challengeId") Integer challengeId,
                                                   @AuthenticationPrincipal MyUserDetails user){
       List<InvitationMember> newFriendList = groupChallengeService.getInviList(challengeId);//도전아이디
       List<Member> leaderFriendList = friendManageService.getFriendList(user.getId()); //방장의 친구목록
       List<Member> notInviList = groupChallengeService.getNotInviList(challengeId,leaderFriendList);
       Map<String, Object> data = new HashMap<>();
       data.put("newFriendList", newFriendList);
       data.put("notInviList", notInviList);
       return ResponseEntity.ok().body(data);
    }

   @DeleteMapping("{challengeId}/members/{userId}")
    public void delete(@PathVariable("userId") Integer userId,
                       @PathVariable("challengeId") Integer challengeId){
       System.out.println("잘됨");
                        invitationService.cancelInvitation(userId,challengeId);
}
}
