package com.doci.webPrj.user.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FreeChallenge {
    int id;
    String name;
    LocalDate startDate;
    LocalDate endDate;
    int period;
    int authFrequency;
    boolean isFinish;
    int goalQuantity;
    int unitId;
    String result;
    boolean isVisibility;
    int memberId;
    int categoryId;
}
