package com.doci.webPrj.user.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    int id;
    Timestamp regDate;
    String content;
    int memberId;
    int performanceRecordsId;
}
