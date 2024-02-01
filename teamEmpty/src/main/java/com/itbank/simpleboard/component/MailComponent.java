package com.itbank.simpleboard.component;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MailComponent {
    private final JavaMailSender mailSender;

    public MailComponent(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(HashMap<String, String> param) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(param.get("to"));
            messageHelper.setSubject(param.get("subject"));
            String content = generateHtmlContent(param.get("authCode"));
            messageHelper.setText(content, true); // second parameter true indicates html
        };
        mailSender.send(messagePreparator);
    }
    private String generateHtmlContent(String authCode) {
        // 이 부분에서 실제 HTML을 작성하시면 됩니다.
        // HTML 템플릿을 수정합니다.
        // 예시로 간단한 HTML 코드를 작성하였습니다.
        return "<fieldset style=\"padding: 10px; text-align: center;\">\n" +
                "<h3>\n" +
                "인증번호는 [ <span style=\"color: blue;\">" + authCode + "</span> ] 입니다\n" +
                "</h3>\n" +
                "</fieldset>";
    }
}

