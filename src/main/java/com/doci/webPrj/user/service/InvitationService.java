package com.doci.webPrj.user.service;

import com.doci.webPrj.user.entity.Invitation;

public interface InvitationService {
        void invite(Invitation invitation);

        void requestAccept(int userId, int challengeId);
}
