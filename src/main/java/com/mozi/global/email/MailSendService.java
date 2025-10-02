package com.mozi.global.email;

import com.mozi.global.exception.BusinessException;
import com.mozi.global.redis.RedisService;
import com.mozi.global.response.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;
    private static final String AUTH_CODE_PREFIX = "AuthCode_";

    private String createAuthCode() {
        return String.valueOf((int) (Math.random() * (900000)) + 100000);
    }

    public void sendEmail(String toEmail) {
        String title = "Mozi 회원가입 인증 이메일 입니다.";
        String authCode = createAuthCode();
        String content =
                "<html>" +
                        "<body style='font-family: Arial, sans-serif;'>" +
                        "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0;'>" +
                        "<h2 style='color: #333;'>Mozi 회원가입 인증 코드 안내</h2>" +
                        "<p><strong>Mozi</strong> 서비스를 이용해주셔서 감사합니다.</p>" +
                        "<p>아래 인증 코드를 회원가입 창에 입력해주세요.</p>" +
                        "<div style='margin: 20px 0; padding: 20px; background-color: #f5f5f5; text-align: center; font-size: 24px; font-weight: bold;'>" +
                        authCode +
                        "</div>" +
                        "<p style='font-size: 12px; color: #888;'>* 이 인증 코드는 5분간 유효합니다.</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

        try {
            MimeMessage emailForm = createEmailForm(toEmail, title, content);
            mailSender.send(emailForm);
        } catch (MessagingException e) {
            throw new BusinessException(ErrorCode.EMAIL_SEND_FAILED);
        }

        redisService.setValuesWithTimeout(AUTH_CODE_PREFIX + toEmail, authCode, 1000 * 60 * 5);
    }

    private MimeMessage createEmailForm(String toEmail, String title, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);
        message.setFrom("dpwl0974@gmail.com");
        message.setText(text, "utf-8", "html");
        return message;
    }
}
