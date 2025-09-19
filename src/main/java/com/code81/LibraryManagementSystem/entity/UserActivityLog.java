package com.code81.LibraryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "UserActivityLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private SystemUser user;

    private String action;

    private LocalDateTime timestamp;
}