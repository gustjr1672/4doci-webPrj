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
public class CommentView {
    int id;
    Timestamp regDate;
    String content;
    String nickName;
    String profileImage;
    int performanceRecordsId;
    int choiceId;
    int freeChallengeId;
    String timeMessage;

}
