package com.mahi.chat.chat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String Content;
    private String Sender;
    private MessageType type;
}
