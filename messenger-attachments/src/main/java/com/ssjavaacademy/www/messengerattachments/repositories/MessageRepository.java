package com.ssjavaacademy.www.messengerattachments.repositories;

import com.ssjavaacademy.www.messengerattachments.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
