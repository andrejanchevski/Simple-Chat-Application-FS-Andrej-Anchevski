package com.fs.chat.application.chatapplication.models;


import com.fs.chat.application.chatapplication.models.enums.MessageType;
import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "chat_room_messages")
public class ChatRoomMessage {

    @PrimaryKeyColumn(name = "chat_room_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long chatRoomId;

    @PrimaryKeyColumn(name = "date_created", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime dateCreated;

    @Column("message_id")
    private String messageId;

    @Column("message_content")
    private String messageContent;

    @Column("message_type")
    private MessageType messageType;

    @Column("user_id")
    private String userId;

    @Column("user_name")
    private String userName;



}
