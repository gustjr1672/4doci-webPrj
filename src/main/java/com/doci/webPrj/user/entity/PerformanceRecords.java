package com.doci.webPrj.user.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class PerformanceRecords {
    private int id;
    private int round;
    private String image;
    private String impression;
    private int achvQuantity;
    private boolean result;
    private Date regDate;
    private Integer freeChallengeId;
    private Integer choiceId;
    private Integer groupStartId;
}
