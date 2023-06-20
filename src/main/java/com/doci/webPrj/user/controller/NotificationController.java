package com.doci.webPrj.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.doci.webPrj.user.service.NotificationService;

@Controller
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

}
