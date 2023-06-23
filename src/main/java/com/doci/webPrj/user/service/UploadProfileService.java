package com.doci.webPrj.user.service;


import com.doci.webPrj.config.MyUserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public interface UploadProfileService {
    public void save(MultipartFile file, MyUserDetails user);

}
