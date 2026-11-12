package by.iba.diploma_101_back.config;

import by.iba.diploma_101_back.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Map;

// проверяет каждую подписку: пациент не может подписаться на чужую переписку
@Component
public class ChatSubscriptionInterceptor implements ChannelInterceptor {
    private final ChatService chatService;

    @Autowired
    public ChatSubscriptionInterceptor(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || !StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            return message;
        }

        Map<String, Object> session = accessor.getSessionAttributes();
        assert session != null;
        int userId = (Integer) session.get(ChatHandshakeInterceptor.USER_ID);
        String role = (String) session.get(ChatHandshakeInterceptor.ROLE);

        if (!chatService.canSubscribe(userId, role, accessor.getDestination())) {
            throw new MessagingException("No access to " + accessor.getDestination());
        }
        return message;
    }
}
