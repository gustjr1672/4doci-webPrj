package com.doci.webPrj.user.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OngoingChallengeView {
    String uniqueId;
    String name;
    int categoryId;
    int unitId;
    int memberId;
    LocalDate startDate;
    LocalDate endDate;
    int goalQuantity;
    int authFrequency;
}
