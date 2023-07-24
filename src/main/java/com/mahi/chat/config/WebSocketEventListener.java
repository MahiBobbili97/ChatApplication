package com.mahi.chat.config;

import com.mahi.chat.chat.ChatMessage;
import com.mahi.chat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event)
    {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username= (String) stompHeaderAccessor.getSessionAttributes().get("Username");
        if(username != null)
        {
            log.info("User disconnected: {}",username);
            var chatMessage= ChatMessage.builder().type(MessageType.LEAVE).Sender(username).build();
            messagingTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
}
