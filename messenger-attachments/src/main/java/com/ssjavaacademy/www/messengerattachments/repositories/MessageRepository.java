package com.ssjavaacademy.www.messengerattachments.repositories;

import com.ssjavaacademy.www.messengerattachments.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByFromUser(String fromUser);
    List<Message> findByIsRead(boolean isRead);

    List<Message> findByToUsers(String username);
}
