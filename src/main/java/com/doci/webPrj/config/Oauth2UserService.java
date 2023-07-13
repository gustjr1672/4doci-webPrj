package com.doci.webPrj.config;

import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String loginId = provider + "_" +providerId;

        Optional<Member> optionalMember = memberRepository.findByUserId(loginId);

        if (optionalMember.isEmpty()) {
            Member member = Member.builder()
                    .userId(loginId)
                    .name(oAuth2User.getAttribute("name"))
                    .email(oAuth2User.getAttribute("email"))
                    .nickname(oAuth2User.getAttribute("name"))
                    .roles("USER").build();

            memberRepository.save(member);
            member.setNickname(oAuth2User.getAttribute("name")+ "" + member.getId());

            return  initMyUserDetail(member);
        }

        return initMyUserDetail(optionalMember.get());
    }

    private MyUserDetails initMyUserDetail(Member member){
        MyUserDetails details = MyUserDetails.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .name(member.getName())
                .email(member.getEmail())
                .pwd(member.getPwd())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .roles(member.getRoles())
                .build();

        return details;
    }
}
