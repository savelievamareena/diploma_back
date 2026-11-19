package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.DTO.ChatMessageDTO;
import by.iba.diploma_101_back.config.ChatHandshakeInterceptor;
import by.iba.diploma_101_back.forms.ChatMessageForm;
import by.iba.diploma_101_back.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Objects;

// сообщения, которые приходят по WebSocket, а не обычными HTTP-запросами
@Controller
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    // клиент отправляет на /app/chat/{patientId}: сохранить и разослать
    @MessageMapping("/chat/{patientId}")
    public void sendMessage(@DestinationVariable int patientId, ChatMessageForm form,
                            SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> session = Objects.requireNonNull(headerAccessor.getSessionAttributes());
        int userId = (Integer) session.get(ChatHandshakeInterceptor.USER_ID);
        String role = (String) session.get(ChatHandshakeInterceptor.ROLE);

        if (!chatService.canAccessChat(userId, role, patientId) || !chatService.isValidText(form.getMessageText())) {
            return;
        }

        ChatMessageDTO message = new ChatMessageDTO(chatService.saveMessage(userId, patientId, form.getMessageText()));
        messagingTemplate.convertAndSend(ChatService.CHAT_TOPIC + patientId, message);
        messagingTemplate.convertAndSend(ChatService.INBOX_TOPIC, message);
    }

    // «печатает…»: в базу не пишется, только рассылается тем, кто открыл эту переписку
    @MessageMapping("/typing/{patientId}")
    public void typing(@DestinationVariable int patientId, SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> session = Objects.requireNonNull(headerAccessor.getSessionAttributes());
        int userId = (Integer) session.get(ChatHandshakeInterceptor.USER_ID);
        String role = (String) session.get(ChatHandshakeInterceptor.ROLE);

        if (!chatService.canAccessChat(userId, role, patientId)) {
            return;
        }

        messagingTemplate.convertAndSend(ChatService.TYPING_TOPIC + patientId, Map.of("senderId", userId));
    }
}
