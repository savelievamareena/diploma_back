package by.iba.diploma_101_back.controller;

import by.iba.diploma_101_back.DTO.ChatMessageDTO;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

// история переписки обычными HTTP-запросами: при открытии чата и после обрыва соединения
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ChatHistoryController {
    private final ChatService chatService;

    @Autowired
    public ChatHistoryController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{patientId}")
    public ResponseEntity<?> getChat(@PathVariable(value = "patientId") int patientId,
                                     @CookieValue(name = "userId", required = false) String userId,
                                     @CookieValue(name = "authKey", required = false) String authKey) {
        User user = chatService.findLoggedInUser(userId, authKey);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!chatService.canAccessChat(user.getId(), user.getRole(), patientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(chatService.findChat(patientId).stream()
                .map(ChatMessageDTO::new)
                .collect(Collectors.toList()));
    }

    // список переписок для admin: последнее сообщение каждого пациента
    @GetMapping("/admin/chats")
    public ResponseEntity<?> getChats(@CookieValue(name = "userId", required = false) String userId,
                                      @CookieValue(name = "authKey", required = false) String authKey) {
        User user = chatService.findLoggedInUser(userId, authKey);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!chatService.isAdmin(user.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(chatService.findLastMessagePerPatient().stream()
                .map(ChatMessageDTO::new)
                .collect(Collectors.toList()));
    }
}
