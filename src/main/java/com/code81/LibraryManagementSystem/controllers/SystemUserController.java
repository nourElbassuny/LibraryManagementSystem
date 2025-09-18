package com.code81.LibraryManagementSystem.controllers;

import com.code81.LibraryManagementSystem.dto.SystemUserRequest;
import com.code81.LibraryManagementSystem.dto.SystemUserResponse;
import com.code81.LibraryManagementSystem.entity.SystemUser;
import com.code81.LibraryManagementSystem.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService userService;

    @PostMapping
    public ResponseEntity<SystemUserResponse> createUser(@RequestBody SystemUserRequest request) {
        SystemUserResponse response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemUserResponse> updateUser(
            @PathVariable Integer id,
            @RequestBody SystemUserRequest request
    ) {
        SystemUserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<SystemUserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
