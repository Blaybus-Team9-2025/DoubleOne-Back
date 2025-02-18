package org.doubleone.domain.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private final Map<String, String>verificationCodes = new HashMap<>();
    private final Map<String, Long> expirationTimes = new HashMap<>();

    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            verificationCode.append(random.nextInt(10));
        }
        return verificationCode.toString();

    }

    private void sendEmail(String toMail, String subject, String body) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);

        javaMailSender.send(mimeMessage);
    }

    // 회원가입 인증 이메일 보내기
    public void sendVerificationEmail(String toMail) throws MessagingException {
        String verificationCode = generateVerificationCode();
        String subject = "이메일 인증을 완료하세요!";
        String body = "<html>"
            + "<head>"
            + "<style>"
            + "  body { font-family: 'Arial', sans-serif; background-color: #f4f4f9; padding: 20px; margin: 0; }"
            + "  .container { width: 100%; max-width: 600px; background-color: #ffffff; border-radius: 10px; padding: 20px; margin: 0 auto; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }"
            + "  .header { font-family: 'Georgia', serif; font-size: 24px; font-weight: bold; color: #4CAF50; text-align: center; margin-bottom: 20px; }"
            + "  .body-text { font-size: 16px; color: #333333; line-height: 1.6; text-align: center; margin-bottom: 20px; }"
            + "  .verification-code { font-size: 18px; font-weight: bold; color: #FF5722; padding: 12px 30px; background-color: #f4f4f9; border-radius: 5px; text-align: center; }"
            + "  .footer { font-size: 12px; color: #888888; text-align: center; margin-top: 30px; }"
            + "  .brand-name { font-family: 'Georgia', serif; color: #4CAF50; font-weight: bold; font-size: 26px; }"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<div class='container'>"
            + "  <div class='header'>온림(溫林) 이메일 인증</div>"
            + "  <div class='body-text'>아래 인증번호를 입력하셔서 인증을 진행해 주세요.</div>"
            + "  <div class='verification-code'>" + verificationCode + "</div>"
            + "  <div class='footer'>"
            + "    <p>이 메일은 온림(溫林)에서 발송한 자동 이메일입니다. 만약, 본 이메일을 요청하지 않으셨다면 무시하셔도 됩니다.</p>"
            + "    <p class='brand-name'>온림(溫林)</p>"
            + "  </div>"
            + "</div>"
            + "</body>"
            + "</html>";

        verificationCodes.put(toMail, verificationCode);
        expirationTimes.put(toMail, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));

        sendEmail(toMail, subject, body);
    }

    // 비밀번호 재설정 인증번호 이메일 보내기
//    public void sendPasswordResetEmail(String toMail) throws MessagingException {
//        String verificationCode = generateVerificationCode();  // 인증번호 생성
//        String subject = "비밀번호 재설정 인증번호";
//        String body = "<h1>비밀번호 재설정을 위한 인증번호</h1>" +
//            "<p>아래 인증번호를 입력하여 비밀번호 재설정을 완료해주세요:</p>" +
//            "<p><strong>" + verificationCode + "</strong></p>";  // 인증번호 포함
//
//        verificationCodes.put(toMail, verificationCode);
//        expirationTimes.put(toMail, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
//
//        sendEmail(toMail, subject, body);
//    }

    public boolean verifyCode(String toMail, String inputCode) {
        String savedCode = verificationCodes.get(toMail);
        Long expirationTime = expirationTimes.get(toMail);

        if (savedCode == null || expirationTime == null || System.currentTimeMillis() > expirationTime) {
            return false;
        }

        return savedCode.equals(inputCode);
    }

}
