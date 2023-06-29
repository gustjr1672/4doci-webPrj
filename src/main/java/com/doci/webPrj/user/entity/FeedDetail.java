package com.doci.webPrj.user.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedDetail {
    int id;
    String impression;
    String image;
    boolean result;
    Date regDate;
    int memberId;
    String nickname;
    String profileImage;
    String challengeName;
    int commentCount;
    Date startDate;
    Date endDate;
}
