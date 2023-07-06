package com.doci.webPrj.user.repository;

import com.doci.webPrj.user.entity.PerformanceRecords;

import java.sql.Timestamp;
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

        void updateRecords(@Param("record") PerformanceRecords performanceRecords);

        void updateResult(@Param("record") PerformanceRecords performanceRecords,
                        @Param("goalQuantity") int goalQuantity, @Param("currentTime") Timestamp currentTime);

        PerformanceRecords findCurrentRecord(@Param("type") String type, @Param("id") int id);

        List<PerformanceRecords> findByResult();

        void updateFail(@Param("record") PerformanceRecords record);
}
