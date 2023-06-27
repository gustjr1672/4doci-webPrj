package com.doci.webPrj.user.entity;

import lombok.Data;

@Data
public class InvitationNotification {
    int groupChallengeId;
    int toMemberId;
    int userId;
    String profileImage;
    String nickname;
}
