package com.fs.chat.application.chatapplication.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "chat_room_messages")
public class ChatRoomMessage {

    @PrimaryKeyColumn(name = "chat_room_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long chatRoomId;

    @PrimaryKeyColumn(name = "date_created", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime dateCreated;

    @Column("message_id")
    private Long messageId;

    @Column("message_content")
    private String messageContent;

    @Column("user_id")
    private Long userId;

    @Column("user_name")
    private String userName;



}
