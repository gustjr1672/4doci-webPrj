package com.doci.webPrj.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRandomList {
    int id;
    String name;
    int period;
    int authFrequency;
    boolean isFinish;
    int unitId;
    int goalQuantity;
    int categoryId;
    String categoryName;
    String image;
}
