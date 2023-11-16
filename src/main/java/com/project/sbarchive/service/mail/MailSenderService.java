package com.project.sbarchive.service.mail;

import javax.mail.internet.MimeMessage;

public interface MailSenderService {

    boolean sendMailByAddMember(String mailTo) throws Exception;

    String getConfirmKey(); // 인증번호

    boolean sendMailByTempPassword(String mailTo) throws Exception;
}
