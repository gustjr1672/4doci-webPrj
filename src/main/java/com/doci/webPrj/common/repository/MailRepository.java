package com.doci.webPrj.common.repository;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MailRepository {

    String findDupEmail(String email);

}
