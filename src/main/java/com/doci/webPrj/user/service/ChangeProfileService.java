package com.doci.webPrj.user.service;


import com.doci.webPrj.config.MyUserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface ChangeProfileService {
    public void save(MultipartFile file, MyUserDetails user);


    void update(String fileName, MyUserDetails user);
}
