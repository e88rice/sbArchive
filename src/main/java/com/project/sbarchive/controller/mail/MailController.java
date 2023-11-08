package com.project.sbarchive.controller.mail;

import com.project.sbarchive.service.mail.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MailController {

    private final MailSenderService mailSenderService;

    @GetMapping("/sendConfirmMail")
    @ResponseBody
    public String sendCofirmMail(String mailTo, HttpSession session) throws Exception {
        if(mailSenderService.sendMailByAddMember(mailTo)) { // 제대로 발송이 됐다면
            session.setAttribute("confirmKey", mailSenderService.getConfirmKey()); // 현재 발급한 인증키를 세션에 저장하고
            return "true"; // "true" 반환
        } else {
            return "false";
        }
    }


}
