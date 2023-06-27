package com.doci.webPrj.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PerformanceRecordsRepository {
    void updateAchvQuantity(@Param("FCId") Integer freeChallengeId,
                            @Param("CHId")Integer ChoiceId,
                            @Param("GSId") Integer GroupStartId);

    int getAchvQuantity(@Param("FCId") Integer freeChallengeId,
                        @Param("CHId")Integer ChoiceId,
                        @Param("GSId") Integer GroupStartId);
}
