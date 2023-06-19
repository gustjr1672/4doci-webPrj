package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImp implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Optional<Member> findOne(String userId) {
        return memberRepository.findById(userId);
    }
}
