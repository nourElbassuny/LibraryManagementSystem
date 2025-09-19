package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    List<UserActivityLog> findByUserUsername(String username);
}
