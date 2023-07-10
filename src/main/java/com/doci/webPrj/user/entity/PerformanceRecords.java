package com.doci.webPrj.user.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class PerformanceRecords {
    private int id;
    private int round;
    private String image;
    private String impression;
    private int achvQuantity;
    private String result;
    private Timestamp regDate;
    private Integer freeChallengeId;
    private Integer choiceId;
    private Integer groupStartId;
}
