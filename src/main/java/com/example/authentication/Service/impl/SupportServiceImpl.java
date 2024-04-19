package com.example.authentication.Service.impl;

import com.example.authentication.Service.SupportService;
import com.example.authentication.request.SenderEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    @Override
    public void setSenderEmail(SenderEmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(request.getSubject());
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(request.getSendFromAddress());
            helper.setTo(request.getSendToAddress());
            helper.setText(request.getMsg(), true);
            mailSender.send(message);

            log.info(messageSource.getMessage(
                    "mail.send-success",
                    new String[]{request.getSendToAddress()},
                    LocaleContextHolder.getLocale()
            ));
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("mail.send-failed");
        }
    }
}
