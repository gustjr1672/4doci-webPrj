package com.doci.webPrj.user.service;

import com.doci.webPrj.config.MyUserDetails;
import com.doci.webPrj.user.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ChangeProfileServiceImp implements ChangeProfileService {

    @Autowired
    MemberRepository memberRepository;

    @Value("${profile.dir}")
    private String profileDir;


    @Override
    public void save(MultipartFile file, MyUserDetails user) {
        String filename = file.getOriginalFilename();
        String fullPath = profileDir + createStoreFileName(filename);

        localDirectorySave(file,fullPath);
        DBSave(fullPath , user);

    }
    @Override
    public void update(String fileName, MyUserDetails user) {
        DBSave(fileName,user);
    }

    private void DBSave(String fullPath , MyUserDetails user){
        String relativePath = fullPath.substring(fullPath.indexOf("/upload"));
        user.setProfileImage(relativePath);

        System.out.println("relativePath = " + relativePath);
        memberRepository.updateProfileImage(user);
    }

    private void localDirectorySave(MultipartFile file, String fullPath) {
        try {
            Path storePath = Path.of(fullPath);
            byte[] fileBytes = file.getBytes();  //파일 데이터 byte로 읽어옴

            Files.write(storePath, fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //파일 이름 난수화
    private String createStoreFileName(String originFilename) {
        String ext = extractExt(originFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //파일의 확장자만 추출
    private String extractExt(String originFilename) {
        int pos = originFilename.lastIndexOf(".");
        return originFilename.substring(pos + 1);
    }
}
