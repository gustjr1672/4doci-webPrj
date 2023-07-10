package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.AllChallenges;
import com.doci.webPrj.user.entity.PerformanceRecords;
import com.doci.webPrj.user.repository.AllChallengesRepository;
import com.doci.webPrj.user.repository.ChoiceRepository;
import com.doci.webPrj.user.repository.FreeChallengeRepository;
import com.doci.webPrj.user.repository.PerformanceRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PerformanceRecordsServiceImp implements PerformanceRecordsService {

    private final String FREECHALLENGE = "FC";
    private final String CHOICE = "CH";
    private final String GROUPSTART = "GS";
    private final String UNDERBAR = "_";

    @Autowired
    PerformanceRecordsRepository recordsRepository;

    @Autowired
    FreeChallengeRepository freeChallengeRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    AllChallengesRepository allChallengesRepository;

    @Override
    public void updateAchvQuantity(String challengeTypeAndId) {
        List<Object> typeAndId = SeparateTypeAndId(challengeTypeAndId);
        String type = (String) typeAndId.get(0);
        int id = (int) typeAndId.get(1);

        switch (type) {
            case FREECHALLENGE:
                recordsRepository.updateAchvQuantity(id, null, null);
                break;
            case CHOICE:
                recordsRepository.updateAchvQuantity(null, id, null);
                break;
            case GROUPSTART:
                recordsRepository.updateAchvQuantity(null, null, id);
                break;
        }
    }

    @Override
    public int getAchvQuantity(String challengeTypeAndId) {
        List<Object> typeAndId = SeparateTypeAndId(challengeTypeAndId);
        String type = (String) typeAndId.get(0);
        int id = (int) typeAndId.get(1);

        int achvQuantity = 0;
        switch (type) {
            case FREECHALLENGE:
                achvQuantity = recordsRepository.getAchvQuantity(id, null, null);
                break;
            case CHOICE:
                achvQuantity = recordsRepository.getAchvQuantity(null, id, null);
                break;
            case GROUPSTART:
                achvQuantity = recordsRepository.getAchvQuantity(null, null, id);
                break;
        }
        return achvQuantity;
    }

    @Override
    public List<PerformanceRecords> getList(String challengeId) {

        List<Object> typeAndId = SeparateTypeAndId(challengeId);
        String type = (String) typeAndId.get(0);
        int id = (int) typeAndId.get(1);

        List<PerformanceRecords> performanceRecordList = null;

        switch (type) {
            case FREECHALLENGE:
                performanceRecordList = recordsRepository.findList(id, null, null);
                break;
            case CHOICE:
                performanceRecordList = recordsRepository.findList(null, id, null);
                break;
            case GROUPSTART:
                performanceRecordList = recordsRepository.findList(null, null, id);
                break;
        }

        return performanceRecordList;
    }

    @Override
    public void editRecords(PerformanceRecords performanceRecords) {
        if (performanceRecords.getImpression().equals(""))
            performanceRecords.setImpression(null);

        System.out.println(performanceRecords.getImpression());
        recordsRepository.updateRecords(performanceRecords);

    }

    @Override
    public void deleteChallenge(String challengeId) {

        List<Object> typeAndId = SeparateTypeAndId(challengeId);
        String type = (String) typeAndId.get(0);
        int id = (int) typeAndId.get(1);

        switch (type) {
            case FREECHALLENGE:
                freeChallengeRepository.delete(id);
                break;
            case CHOICE:
                choiceRepository.delete(id);
                break;
            case GROUPSTART:
                // 그룹도전 합치고 구현
                break;
        }

    }

    private List<Object> SeparateTypeAndId(String challengeId) {
        List<Object> result = new ArrayList<>();

        int index = challengeId.indexOf(UNDERBAR);
        String challengeType = challengeId.substring(0, index);
        int id = Integer.parseInt(challengeId.substring(index + 1));

        result.add(challengeType);
        result.add(id);

        return result;
    }

    @Override
    public void updateResultOfRound(PerformanceRecords performanceRecords, String uniqueId) {

        AllChallenges allChallenges = allChallengesRepository.findChallenge(uniqueId);
        int goalQuantity = allChallenges.getGoalQuantity();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        recordsRepository.updateResult(performanceRecords, goalQuantity, currentTime);
    }

    @Override
    public PerformanceRecords getCurrentRecord(String challengeId) {

        List<Object> typeAndId = SeparateTypeAndId(challengeId);
        String type = (String) typeAndId.get(0);
        int id = (int) typeAndId.get(1);

        PerformanceRecords performanceRecords = recordsRepository.findCurrentRecord(type, id);

        return performanceRecords;
    }

}
