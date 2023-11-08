package com.project.sbarchive.service.mail;

public interface MailSenderService {

    boolean sendMailByAddMember(String mailTo) throws Exception;

    String getConfirmKey(); // 인증번호


}
