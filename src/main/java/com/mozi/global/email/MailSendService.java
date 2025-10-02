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
                "Mozi 서비스를 이용해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + authCode + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";

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
