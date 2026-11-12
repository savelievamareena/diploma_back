package by.iba.diploma_101_back.DTO;

import by.iba.diploma_101_back.model.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;

// то, что уходит клиенту: без User целиком, иначе в JSON попадёт хэш пароля
@Getter
public class ChatMessageDTO {
    private final int id;
    private final int patientId;
    private final int senderId;
    private final String senderName;
    private final String senderRole;
    private final String messageText;
    private final LocalDateTime createdAt;

    public ChatMessageDTO(ChatMessage message) {
        this.id = message.getId();
        this.patientId = message.getPatient().getId();
        this.senderId = message.getSender().getId();
        this.senderName = message.getSender().getFirstName() + " " + message.getSender().getLastName();
        this.senderRole = message.getSender().getRole();
        this.messageText = message.getMessageText();
        this.createdAt = message.getCreatedAt();
    }
}
