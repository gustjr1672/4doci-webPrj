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
public class CommentNotificationView {
    private int id;
    private Timestamp regDate;
    private int fromMemberId;
    private String nickName;
    private String profileImage;
    private int performanceRecordsId;
    private int toMemberId;
    private String timeMessage;
}
