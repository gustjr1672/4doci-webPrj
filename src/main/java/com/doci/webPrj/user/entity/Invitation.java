package com.doci.webPrj.user.entity;

import lombok.Data;

@Data
public class Invitation {
    int freeChallengeId;
    int toMemberId;
    int fromMemberId;
    boolean isAccept;
}
