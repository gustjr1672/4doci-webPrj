package com.doci.webPrj.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeFormView {
    String uniqueId;
    String name;
    int categoryId;
    int unitId;
    int goalQuantity;
    int authFrequency;
}
