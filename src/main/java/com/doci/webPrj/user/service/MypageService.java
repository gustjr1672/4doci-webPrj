package com.doci.webPrj.user.service;

import java.util.List;

import com.doci.webPrj.user.entity.PastChallengeView;

public interface MypageService {

    List<PastChallengeView> getPastChallenge(int id);

}
