package com.doci.webPrj.user.repository;

import com.doci.webPrj.user.entity.PerformanceRecords;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PerformanceRecordsRepository {
        void save(PerformanceRecords performanceRecords);

        void updateAchvQuantity(@Param("FCId") Integer freeChallengeId,
                        @Param("CHId") Integer ChoiceId,
                        @Param("GSId") Integer GroupStartId);

        int getAchvQuantity(@Param("FCId") Integer freeChallengeId,
                        @Param("CHId") Integer ChoiceId,
                        @Param("GSId") Integer GroupStartId);

        List<PerformanceRecords> findList(@Param("FCId") Integer freeChallengeId,
                        @Param("CHId") Integer ChoiceId,
                        @Param("GSId") Integer GroupStartId);

        void updateRecords(@Param("imp") String impression, @Param("achv") int achvQuantity,
                        @Param("id") int id);

        void updateResult(@Param("achvQuantity") int achvQuantity, @Param("goalQuantity") int goalQuantity,
                        @Param("id") int id);

        PerformanceRecords findCurrentRecord(@Param("type") String type, @Param("id") int id);
}
