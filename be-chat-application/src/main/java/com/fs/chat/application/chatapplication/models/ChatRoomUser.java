package com.fs.chat.application.chatapplication.models;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "chat_room_users")
public class ChatRoomUser {
    @PrimaryKeyColumn(name = "chat_room_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long chatRoomId;

    @PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Long userId;

    @Column("user_name")
    private String userName;
}
