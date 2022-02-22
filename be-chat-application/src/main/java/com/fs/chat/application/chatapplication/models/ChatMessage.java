package com.fs.chat.application.chatapplication.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatMessage {
    private String senderName;
    private String body;
    private String date;
    private MessageType messageType;
}
