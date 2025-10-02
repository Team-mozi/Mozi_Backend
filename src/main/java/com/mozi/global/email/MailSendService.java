package com.mozi.global.email;

import com.mozi.global.exception.BusinessException;
import com.mozi.global.redis.RedisService;
import com.mozi.global.response.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;

    private final RedisService redisService;

    private static final String AUTH_CODE_PREFIX = "AuthCode_";

    private final SpringTemplateEngine templateEngine;

    private String createAuthCode() {
        return String.valueOf((int) (Math.random() * (900000)) + 100000);
    }

    public void sendEmail(String toEmail) {
        String title = "Mozi 회원가입 인증 이메일 입니다.";
        String authCode = createAuthCode();

        try {
            MimeMessage emailForm = createEmailForm(toEmail, title, authCode);
            mailSender.send(emailForm);
        } catch (MessagingException e) {
            throw new BusinessException(ErrorCode.EMAIL_SEND_FAILED);
        }
        redisService.setValuesWithTimeout(AUTH_CODE_PREFIX + toEmail, authCode, 1000 * 60 * 5);
    }

    private MimeMessage createEmailForm(String toEmail, String title, String authCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);
        message.setFrom("dpwl0974@gmail.com");

        Context context = new Context();
        context.setVariable("authCode", authCode);

        String htmlContent = templateEngine.process("mail/verificationCode", context);
        message.setText(htmlContent, "utf-8", "html");

        return message;
    }
}
