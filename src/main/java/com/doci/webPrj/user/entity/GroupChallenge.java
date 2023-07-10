package com.doci.webPrj.user.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class GroupChallenge {
    int id;
    String name;
    LocalDate startDate;
    int startTime;
    LocalDate endDate;
    int period;
    int authFrequency;
    boolean isFinish;
    int goalQuantity;
    int unitId;
    int groupLeaderId;
    int categoryId;
}
