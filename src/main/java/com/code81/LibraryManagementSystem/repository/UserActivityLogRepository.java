package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
}
