package by.iba.diploma_101_back.service;

import by.iba.diploma_101_back.exception.ResourceNotFoundException;
import by.iba.diploma_101_back.model.ChatMessage;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.ChatMessageRepository;
import by.iba.diploma_101_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ChatService {
    public static final String CHAT_TOPIC = "/topic/chat/";
    public static final String TYPING_TOPIC = "/topic/typing/";
    public static final String INBOX_TOPIC = "/topic/inbox";

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository, UserRepository userRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    // пользователь по cookies userId и authKey, которые ставит AuthController при входе;
    // null, если cookies нет или они не подходят
    public User findLoggedInUser(String userId, String authKey) {
        if (userId == null || authKey == null) {
            return null;
        }
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            return null;
        }
        User user = userRepository.findById(id).orElse(null);
        if (user == null || !Objects.equals(user.getPassword(), authKey)) {
            return null;
        }
        return user;
    }

    // пациент видит только свою переписку с клиникой, admin видит переписку с любым пациентом
    public boolean canAccessChat(int userId, String role, int patientId) {
        return isAdmin(role) || userId == patientId;
    }

    public boolean canSubscribe(int userId, String role, String destination) {
        if (destination == null) {
            return false;
        }
        if (destination.equals(INBOX_TOPIC)) {
            return isAdmin(role);
        }
        if (destination.startsWith(CHAT_TOPIC)) {
            return canAccessChatIn(userId, role, destination.substring(CHAT_TOPIC.length()));
        }
        if (destination.startsWith(TYPING_TOPIC)) {
            return canAccessChatIn(userId, role, destination.substring(TYPING_TOPIC.length()));
        }
        return false;
    }

    public boolean isValidText(String messageText) {
        return messageText != null && !messageText.isBlank() && messageText.length() <= ChatMessage.MAX_LENGTH;
    }

    public ChatMessage saveMessage(int senderId, int patientId, String messageText) {
        ChatMessage message = new ChatMessage();
        message.setSender(userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", senderId)));
        message.setPatient(userRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", patientId)));
        message.setMessageText(messageText.trim());
        message.setCreatedAt(LocalDateTime.now());
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> findChat(int patientId) {
        return chatMessageRepository.findByPatientIdOrderByIdAsc(patientId);
    }

    public List<ChatMessage> findLastMessagePerPatient() {
        return chatMessageRepository.findLastMessagePerPatient();
    }

    public boolean isAdmin(String role) {
        return "admin".equals(role);
    }

    private boolean canAccessChatIn(int userId, String role, String patientIdPart) {
        try {
            return canAccessChat(userId, role, Integer.parseInt(patientIdPart));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
