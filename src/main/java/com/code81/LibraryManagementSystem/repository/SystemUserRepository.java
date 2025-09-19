package com.code81.LibraryManagementSystem.repository;

import com.code81.LibraryManagementSystem.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {
    Optional<SystemUser> findByUsername(String username);

}
