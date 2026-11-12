package by.iba.diploma_101_back.config;

import by.iba.diploma_101_back.model.User;
import by.iba.diploma_101_back.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

// срабатывает один раз, когда браузер открывает соединение на /ws:
// пускает только вошедшего пользователя и запоминает его id и роль на всё соединение
@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {
    public static final String USER_ID = "userId";
    public static final String ROLE = "role";

    private final ChatService chatService;

    @Autowired
    public ChatHandshakeInterceptor(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        User user = chatService.findLoggedInUser(cookieValue(servletRequest, "userId"),
                cookieValue(servletRequest, "authKey"));

        if (user == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        attributes.put(USER_ID, user.getId());
        attributes.put(ROLE, user.getRole());
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }

    private static String cookieValue(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        return cookie == null ? null : cookie.getValue();
    }
}
