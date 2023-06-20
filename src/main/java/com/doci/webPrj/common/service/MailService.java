package com.doci.webPrj.common.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;

@Service
public class MailService {

    @Autowired
    JavaMailSender emailSender;


    private String authNum;

    
    public MimeMessage createMessage(String toEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        
        message.addRecipients(RecipientType.TO, toEmail);// 보내는 대상
		message.setSubject("4도씨 회원가입 이메일 인증");
       

        String content = "";
		content += "<div style='margin:100px;'>";
		content += "<h1> 안녕하세요! 4도씨입니다.</h1>";
		content += "<p> 4도씨 회원가입을 위해</p>";
		content += "<br>";
		content += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
		content += "<br>";
		content += "<p>당신의 도전을 응원합니다!<p>";
		content += "<br>";
		content += "<div align='center' style='border:2px solid black;';>";
		content += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
		content += "<div style='font-size:130%'>";
		content += "CODE : <strong>";
		content += authNum + "</strong><div><br/> "; // 메일에 인증번호 넣기
		content += "</div>";
		message.setText(content, "utf-8", "html");
        message.setFrom(new InternetAddress("4_docci@naver.com", "4_docci_Admin"));

        return message;
    }

    
    public String createKey() {
        Random rand = new Random();
        StringBuilder key = new StringBuilder(6);
        for(int i=0;i<6;i++){
            key.append(rand.nextInt(10));
        }
        return key.toString();
    }

    public String sendMessage(String toEmail) {
        authNum = createKey();

        try {// 예외처리
            MimeMessage message = createMessage(toEmail);
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return authNum;
    }
    
}
