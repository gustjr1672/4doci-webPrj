package com.doci.webPrj.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.doci.webPrj.user.entity.ChallengeFormView;
import com.doci.webPrj.user.entity.OngoingChallengeView;
import com.doci.webPrj.user.entity.PastChallengeView;

@Mapper
public interface ChallengeViewRepository {

    List<OngoingChallengeView> getOngoingList(int id);

    List<PastChallengeView> getPastList(int id);

    ChallengeFormView getChallengeFormById(String uniqueId);

}
