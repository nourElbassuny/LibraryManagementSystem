package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.entity.SystemUser;
import com.code81.LibraryManagementSystem.entity.UserActivityLog;
import com.code81.LibraryManagementSystem.repository.UserActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityLogService {
    private final UserActivityLogRepository logRepository;

    public void save(SystemUser user, String action, String details) {
        UserActivityLog log = UserActivityLog.builder()
                .user(user)
                .action(action)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
        logRepository.save(log);
    }
    public List<UserActivityLog> getAllLogs() {
        return logRepository.findAll();
    }
}
