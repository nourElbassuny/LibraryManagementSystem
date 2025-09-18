package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.SystemUserRequest;
import com.code81.LibraryManagementSystem.dto.SystemUserResponse;
import com.code81.LibraryManagementSystem.entity.Role;
import com.code81.LibraryManagementSystem.entity.SystemUser;
import com.code81.LibraryManagementSystem.repository.RoleRepository;
import com.code81.LibraryManagementSystem.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemUserService {

    private final SystemUserRepository userRepository;
    private final RoleRepository roleRepository;

    public SystemUserResponse createUser(SystemUserRequest request) {
        SystemUser user = new SystemUser();
        user.setUsername(request.username());
        user.setPassword(request.password()); // encode later
        user.setEmail(request.email());
        user.setStatus(request.status());

        Set<Role> roles = new HashSet<>();
        if (request.roleIds() != null) {
            for (Integer roleId : request.roleIds()) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
        }
        user.setRoles(roles);

        SystemUser savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Transactional
    public SystemUserResponse updateUser(Integer userId, SystemUserRequest request) {
        SystemUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // update basic fields
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setStatus(request.status());

        // update password only if provided
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(request.password()); // 🔐 encode later with BCrypt
        }

        // update roles
        Set<Role> roles = new HashSet<>();
        if (request.roleIds() != null) {
            for (Integer roleId : request.roleIds()) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
        }
        user.setRoles(roles);

        SystemUser updated = userRepository.save(user);
        return mapToResponse(updated);
    }



    public SystemUserResponse getUserById(Integer id) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return mapToResponse(user);
    }
    public List<SystemUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public void deleteUser(Integer id) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        userRepository.delete(user);
    }

    private SystemUserResponse mapToResponse(SystemUser user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new SystemUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                roleNames
        );
    }
}
