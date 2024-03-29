package com.doci.webPrj.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.doci.webPrj.user.entity.Member;
import com.doci.webPrj.user.repository.MemberRepository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationHandler extends TextWebSocketHandler {
    // 전체 로그인 유저
    private List<WebSocketSession> sessions = new ArrayList<>();
    // 1대1 매핑
    private Map<String, WebSocketSession> userSessionMap = new HashMap<>();
    @Autowired
    MemberRepository memberRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // System.out.println("Socket 연결");
        sessions.add(session);
        System.out.println(sendPushUsername(session));
        String senderId = sendPushUsername(session);
        userSessionMap.put(senderId, session);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("session = " + sendPushUsername(session));
        String msg = message.getPayload();
        System.out.println("msg = " + msg);
        if (!StringUtils.isEmpty(msg)) {
            String[] strs = msg.split(",");

            if (strs != null && strs.length == 4) {
                String pushCategory = strs[0]; // 친추 그룹초대 댓글 구분
                // String replyWriter = strs[1]; // 친추 그룹초대 댓글 보낸 유저
                int sendedPushUserId = Integer.parseInt(strs[1]); // 푸시 알림 받을 유저
                Member member = memberRepository.findById(sendedPushUserId);
                String UserId = member.getUserId();
                WebSocketSession sendedPushSession = userSessionMap.get(UserId);
                if (sendedPushSession != null) {
                    TextMessage textMsg = new TextMessage("새로운 알람이 도착했습니다.");
                    sendedPushSession.sendMessage(textMsg);
                }

            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // System.out.println("Socket 연결 해제");
        sessions.remove(session);
        userSessionMap.remove(sendPushUsername(session), session);
    }

    // 알람을 보내는 유저(친구추가 유저)
    private String sendPushUsername(WebSocketSession session) {
        String loginUsername;

        if (session.getPrincipal() == null) {
            loginUsername = null;
        } else {
            loginUsername = session.getPrincipal().getName();
        }
        return loginUsername;
    }
}
