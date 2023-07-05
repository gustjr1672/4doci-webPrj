package com.doci.webPrj.scheduler.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateView {
    int id;
    boolean isFinish;
    int period;
    LocalDate startDate;
    LocalDate endDate;
    int authFrequency;
    int goalQuantity;
    int recordRound;
}
