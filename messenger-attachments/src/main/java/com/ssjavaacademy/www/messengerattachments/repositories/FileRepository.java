package com.ssjavaacademy.www.messengerattachments.repositories;

import com.ssjavaacademy.www.messengerattachments.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
