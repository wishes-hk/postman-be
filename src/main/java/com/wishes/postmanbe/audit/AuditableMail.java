package com.wishes.postmanbe.audit;

import com.wishes.postmanbe.model.Mail;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditableMail {

    public AuditableMail(Mail mail) {
        recipient = mail.getRecipient();
        subject = mail.getSubject();
        content = mail.getContent();
        ccList = mail.getCcList() == null ? null : String.join(";", mail.getCcList());
        bccList = mail.getBccList() == null ? null : String.join(";", mail.getBccList());
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @NonNull
    private String recipient;

    @NonNull
    @Column(length = 4095)
    private String subject;

    @Column(length = 1048575)
    private String content;

    @Column(length = 4095)
    private String ccList;

    @Column(length = 4095)
    private String bccList;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name="updated_date")
    private LocalDateTime lastModifiedDate;

}
