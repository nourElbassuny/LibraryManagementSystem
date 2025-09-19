package com.code81.LibraryManagementSystem.dto.response;

import java.time.LocalDateTime;

public record UserActivityLogResponse(Integer logId,
                                      Integer userId,
                                      String username,
                                      String action,
                                      LocalDateTime timestamp) {
}
