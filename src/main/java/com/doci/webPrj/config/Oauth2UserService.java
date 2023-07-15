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

import java.util.Map;
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

        OAuth2UserInfo oAuth2UserInfo = checkProvider(provider,oAuth2User);

        String providerId = oAuth2UserInfo.getProviderId();
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String nickname = oAuth2UserInfo.getName();


        Optional<Member> optionalMember = memberRepository.findByUserId(loginId);

        if (optionalMember.isEmpty()) {
            Member member = Member.builder()
                    .userId(loginId)
                    .name(name)
                    .email(email)
                    .nickname(nickname)
                    .roles("USER").build();

            memberRepository.save(member);
            member.setNickname(oAuth2User.getAttribute("name")+ "" + member.getId());

            return  initMyUserDetail(member);
        }

        return initMyUserDetail(optionalMember.get());
    }

    private OAuth2UserInfo checkProvider(String provider,OAuth2User oAuth2User){
        switch (provider){
            case "google":{
                log.info("구글로그인 요청");
                return new GoogleUserInfo(oAuth2User.getAttributes());
            }
            case "naver":{
                log.info("네이버로그인 요청");
                return new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
            }
            case "kakao":{
                log.info("카카오로그인 요청");
                return new KakaoUserInfo(oAuth2User.getAttributes());
            }
            default:{
                return null;
            }
        }
    }

//    private Member initMember(){
//        Member member;
//
//        return member;
//    }
    private MyUserDetails initMyUserDetail(Member member) {
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
        String roles = details.getRoles();
        System.out.println("roles = " + roles);
        return details;
    }
}
