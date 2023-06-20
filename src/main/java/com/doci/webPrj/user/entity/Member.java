package com.doci.webPrj.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private int id;
    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String pwd;
    private String profileImage;
    private Date regDate;
    private String roles;
}
