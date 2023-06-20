package com.doci.webPrj.user.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequest {
    int toMemberId;
    int fromMemberId;
    boolean isAccept;
}
