package com.fs.chat.application.chatapplication.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatMessage {
    private String senderName;
    private String messageBody;
    private String dateCreated;
    private MessageType messageType;
}
