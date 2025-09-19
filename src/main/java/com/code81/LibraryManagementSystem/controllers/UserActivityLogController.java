package com.code81.LibraryManagementSystem.controllers;

import com.code81.LibraryManagementSystem.dto.response.UserActivityLogResponse;
import com.code81.LibraryManagementSystem.service.UserActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class UserActivityLogController {
    private final UserActivityLogService logService;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<UserActivityLogResponse>> getLogsBySystemUsername(@PathVariable String username) {
        return ResponseEntity.ok(logService.getLogsByUsername(username));
    }
    @GetMapping
    public ResponseEntity<List<UserActivityLogResponse>> getLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

}
