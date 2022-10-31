package com.ssjavaacademy.www.messengerattachments.repositories;

import com.ssjavaacademy.www.messengerattachments.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByFromUser(String fromUser);
    List<Message> findByIsRead(boolean isRead);
    List<Message> findByToUsers(String username);

    @Query(value = "select * from messages s where s.is_read =:isRead and s.to_user like %:keyword%", nativeQuery = true)
    List<Message> findByKeyword(@Param("keyword") String keyword, @Param("isRead") boolean isRead);
}
