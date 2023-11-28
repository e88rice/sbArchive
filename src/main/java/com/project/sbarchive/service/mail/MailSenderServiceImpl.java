package com.project.sbarchive.service.mail;

import com.project.sbarchive.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService{

    private final JavaMailSender mailSender; // 메일을 보내는 역할

    private final TemplateEngine templateEngine;

    private final UserService userService;

    private String confirmKey; // 인증번호

    @Value("${myapp.custom.mail.sender.mailFrom}")
    private String mailFrom; //보내는 메일

    @Value("${myapp.custom.mail.sender.mailFromName}")
    private String mailFromName;

    @Override
    public boolean sendMailByAddMember(String mailTo) throws Exception { // 회원 가입시 인증 메일 발송
        this.confirmKey = createConfirmKey(); // 키 생성
        MimeMessage message = createMessageByAddMember(mailTo); // 메일 내용 작성
        try { // 예외처리
            mailSender.send(message); // 메일 발송
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getConfirmKey() {
        return this.confirmKey;
    }

    private MimeMessage createMessageByAddMember(String mailTo) throws Exception {
        // 회원 가입시 인증 메일 관련 내용 작성
        log.info("보내는 대상 : " + mailTo);
        log.info("인증 번호 : " + confirmKey);

        // 메일 템플릿에 전달할 데이터 설정
        Context context = new Context();
        context.setVariable("confirmKey", confirmKey);

        // Thymeleaf 템플릿 엔진을 사용하여 메일 본문 생성
        String messageText = templateEngine.process("/mail/add_member", context);

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, mailTo); // 보내는 대상
        message.setSubject(mailFromName + " - 이메일 인증코드"); // 제목
        message.setText(messageText, "utf-8", "html");
        message.setFrom(new InternetAddress(mailFrom, mailFromName));  // 보내는 사람

        return message;
    }

    // static이라 클래스 이름으로 사용 가능
    private static String createConfirmKey() {
        // 인증키 작성
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for(int i=0; i<8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97= 98 => (char) 98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            } // switch 문 끝

        } // for 문 끝

        return key.toString();
    }



    @Override
    public boolean sendMailByTempPassword(String mailTo) throws Exception { // 회원 가입시 인증 메일 발송
        MimeMessage message = createMailAndChangePassword(mailTo); // 메일 내용 작성
        try { // 예외처리
            mailSender.send(message); // 메일 발송
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public MimeMessage createMailAndChangePassword(String email) throws Exception{
        // 회원 가입시 인증 메일 관련 내용 작성
        log.info("보내는 대상 : " + email);
        String str = getTempPassword();

        // 메일 템플릿에 전달할 데이터 설정
        Context context = new Context();
        context.setVariable("tempPassword", str);

        // Thymeleaf 템플릿 엔진을 사용하여 메일 본문 생성
        String messageText = templateEngine.process("/mail/temp_password", context);

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email); // 보내는 대상
        message.setSubject(mailFromName + " - 임시비밀번호 안내"); // 제목
        message.setText(messageText, "utf-8", "html");
        message.setFrom(new InternetAddress(mailFrom, mailFromName));  // 보내는 사람

        updateTempPassword(str, email);

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  " + str + "  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        return message;
    }

    public void updateTempPassword(String str,String email){
        String id = userService.getUserId(email);
        userService.updateTempPassword(id, str);
    }

    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }


}
