package com.doci.webPrj.user.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AllChallenges {
    String uniqueId;
    String name;
    String unitName;
    int memberId;
    LocalDate startDate;
    LocalDate endDate;
    int goalQuantity;
    int achvQuantity;
}