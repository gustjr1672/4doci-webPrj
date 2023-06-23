package com.doci.webPrj.common.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.MemberRepository;

@Service
public class JoinFormService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository repository;

    @Autowired
    public JoinFormService(PasswordEncoder passwordEncoder, MemberRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public boolean isIdValid(String userId) {
        Optional<Member> optionalValue = repository.findByUserId(userId);
        if(optionalValue.isPresent())
            return false;
        return true;
    }


    public boolean isNicknameValid(String nickname) {
        if(repository.findByNickname(nickname) != null)
            return false;
        return true;
    }

    public void join(Member member){
        String pwd = member.getPwd();
        member.setPwd(passwordEncoder.encode(pwd));
        repository.save(member);
    }
}
