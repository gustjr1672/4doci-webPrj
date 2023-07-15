package com.doci.webPrj.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doci.webPrj.user.entity.PastChallengeView;
import com.doci.webPrj.user.repository.ChallengeViewRepository;

@Service
public class MypageServiceImp implements MypageService {

    @Autowired
    ChallengeViewRepository repository;

    @Override
    public List<PastChallengeView> getPastChallenge(int id) {
        return repository.getPastList(id);
    }

}
