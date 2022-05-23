package com.wishes.postmanbe.service;

import com.wishes.postmanbe.audit.AuditableMail;
import com.wishes.postmanbe.model.Mail;
import com.wishes.postmanbe.repo.AuditableMailRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.Map;

@Service
@Slf4j
public class MailingService {

    private final JavaMailSender mailSender;
    private final AuditableMailRepo auditableMailRepo;

    public MailingService(JavaMailSender mailSender, AuditableMailRepo auditableMailRepo) {
        this.mailSender = mailSender;
        this.auditableMailRepo = auditableMailRepo;
    }

    public void deliver(Mail mail) {
        MimeMessage msg = this.mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            mail.setContent(parseText(mail.getContent(), mail.getParams()));

            // Set basic attributes
            helper.setTo(mail.getRecipient());
            helper.setSubject(mail.getSubject());
            if(mail.getContent() != null)
                helper.setText(mail.getContent(), true);
            if(mail.getCcList() != null)
                helper.setCc(mail.getCcList().toArray(String[]::new));
            if(mail.getBccList() != null)
                helper.setBcc(mail.getBccList().toArray(String[]::new));

            this.mailSender.send(msg);
            auditableMailRepo.save(new AuditableMail(mail));
            log.info("Delivered: {} to {}", mail.getSubject(), mail.getRecipient());

        } catch (Exception e) {
            log.error(MessageFormat.format("Unsuccessful Delivery: {0}", e));
        }
    }

    public String parseText(String content, Map<String, String> contentParams) {
        if(content == null) {
            return null;
        }

        if(contentParams == null) {
            return content;
        }

        for(Map.Entry<String, String> entry : contentParams.entrySet()) {
            String key = "\\$\\{" + entry.getKey() + "}";
            content = content.replaceAll(key, entry.getValue());
        }
        return content;
    }
}
