package com.doci.webPrj.user.entity;

import lombok.Data;

@Data
public class Invitation {
    int groupChallengeId;
    int toMemberId;
    boolean isAccept;
}
