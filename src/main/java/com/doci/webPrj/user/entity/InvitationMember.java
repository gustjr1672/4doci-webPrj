package com.doci.webPrj.user.entity;

import lombok.Data;

@Data
public class InvitationMember {
    int groupChallengeId;
    int toMemberId;
    int userId;
    String isAccept;
    String profileImage;
    String nickname;
}
