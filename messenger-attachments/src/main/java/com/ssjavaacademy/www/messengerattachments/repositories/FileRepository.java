package com.ssjavaacademy.www.messengerattachments.repositories;

import com.ssjavaacademy.www.messengerattachments.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFileName(String fileName);
}
