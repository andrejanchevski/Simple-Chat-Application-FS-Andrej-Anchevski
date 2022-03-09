package com.fs.chat.application.chatapplication.repository;

import com.fs.chat.application.chatapplication.models.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
