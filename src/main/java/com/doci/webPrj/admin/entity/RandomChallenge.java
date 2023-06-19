package com.doci.webPrj.admin.entity;


import lombok.Data;

@Data
public class RandomChallenge {
    int id;
    String name;
    int period;
    int authFrequency;
    boolean isFinish;
    int unitId;
    int goalQuantity;
    int categoryId;
    String result;
}
