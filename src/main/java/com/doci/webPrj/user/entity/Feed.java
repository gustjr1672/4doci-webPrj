package com.doci.webPrj.user.entity;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feed {
    int id;
    String impression;
    String image;
    boolean result;
    Timestamp regDate;
    int memberId;
    String nickname;
    String profileImage;
    String challengeName;
    int freeChallengeId;
    int choiceId;
    int groupStartId;
}
