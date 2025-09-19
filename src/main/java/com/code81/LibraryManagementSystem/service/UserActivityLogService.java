package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.response.UserActivityLogResponse;
import com.code81.LibraryManagementSystem.entity.SystemUser;
import com.code81.LibraryManagementSystem.entity.UserActivityLog;
import com.code81.LibraryManagementSystem.repository.SystemUserRepository;
import com.code81.LibraryManagementSystem.repository.UserActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserActivityLogService {
    private final UserActivityLogRepository logRepository;
    private final SystemUserRepository systemUserRepository;

    public void saveLogAction(String action) {
        String name = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        SystemUser user = systemUserRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        UserActivityLog log = UserActivityLog.builder()
                .user(user)
                .action(action)
                .timestamp(LocalDateTime.now())
                .build();
        logRepository.save(log);
    }

    public List<UserActivityLogResponse> getAllLogs() {
        return logRepository.findAll()
                .stream().map(this::mapToResponse)
                .toList();
    }

    public List<UserActivityLogResponse> getLogsByUsername(String username) {
        return logRepository.findByUserUsername(username)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private  UserActivityLogResponse mapToResponse(UserActivityLog log) {
        return new UserActivityLogResponse(
                log.getLogId(),
                log.getUser().getId(),
                log.getUser().getUsername(),
                log.getAction(),
                log.getTimestamp()
        );
    }
}
