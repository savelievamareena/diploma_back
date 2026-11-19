package by.iba.diploma_101_back.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
@Table(name = "chatmessage")
public class ChatMessage extends DataObject {
    public static final int MAX_LENGTH = 2000;

    // пациент, чья это переписка с клиникой
    @ManyToOne
    @JoinColumn(name = "patientId")
    private User patient;

    // автор сообщения: сам пациент или admin клиники
    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;

    @Column(name = "messageText", length = MAX_LENGTH)
    private String messageText;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;
}
