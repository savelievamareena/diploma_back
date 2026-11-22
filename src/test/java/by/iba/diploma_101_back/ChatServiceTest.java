package by.iba.diploma_101_back;

import by.iba.diploma_101_back.model.ChatMessage;
import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.repository.ChatMessageRepository;
import by.iba.diploma_101_back.repository.UserRepository;
import by.iba.diploma_101_back.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// без базы: репозитории подменены Mockito
class ChatServiceTest {
    private UserRepository userRepository;
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        chatService = new ChatService(Mockito.mock(ChatMessageRepository.class), userRepository);
    }

    @Test
    void patientAccessesOnlyOwnChat() {
        assertTrue(chatService.canAccessChat(5, "user", 5));
        assertFalse(chatService.canAccessChat(5, "user", 6));
    }

    @Test
    void adminAccessesAnyChat() {
        assertTrue(chatService.canAccessChat(1, "admin", 6));
    }

    @Test
    void patientSubscribesOnlyToOwnTopics() {
        assertTrue(chatService.canSubscribe(5, "user", "/topic/chat/5"));
        assertTrue(chatService.canSubscribe(5, "user", "/topic/typing/5"));
        assertFalse(chatService.canSubscribe(5, "user", "/topic/chat/6"));
        assertFalse(chatService.canSubscribe(5, "user", "/topic/typing/6"));
        assertFalse(chatService.canSubscribe(5, "user", "/topic/inbox"));
    }

    @Test
    void adminSubscribesToInboxAndAnyChat() {
        assertTrue(chatService.canSubscribe(1, "admin", "/topic/inbox"));
        assertTrue(chatService.canSubscribe(1, "admin", "/topic/chat/6"));
    }

    @Test
    void unknownOrBrokenDestinationIsRejected() {
        assertFalse(chatService.canSubscribe(1, "admin", "/topic/other"));
        assertFalse(chatService.canSubscribe(1, "admin", "/topic/chat/abc"));
        assertFalse(chatService.canSubscribe(1, "admin", null));
    }

    @Test
    void loggedInUserNeedsMatchingAuthKey() {
        User user = new User();
        user.setPassword("HASH");
        when(userRepository.findById(5)).thenReturn(Optional.of(user));

        assertSame(user, chatService.findLoggedInUser("5", "HASH"));
        assertNull(chatService.findLoggedInUser("5", "WRONG"));
        assertNull(chatService.findLoggedInUser("abc", "HASH"));
        assertNull(chatService.findLoggedInUser(null, "HASH"));
    }

    @Test
    void textMustBeNonBlankAndNotTooLong() {
        assertTrue(chatService.isValidText("Здравствуйте"));
        assertFalse(chatService.isValidText("   "));
        assertFalse(chatService.isValidText(null));
        assertFalse(chatService.isValidText("a".repeat(ChatMessage.MAX_LENGTH + 1)));
    }
}
