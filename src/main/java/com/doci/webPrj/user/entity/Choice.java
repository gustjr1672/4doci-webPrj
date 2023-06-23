package com.doci.webPrj.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    int id;
    int memberId;
    int randomChallengeId;
    LocalDate startDate;
    boolean visibility;
    LocalDate endDate;
}
