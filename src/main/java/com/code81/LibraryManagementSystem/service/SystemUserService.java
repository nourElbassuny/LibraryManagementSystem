package com.code81.LibraryManagementSystem.service;

import com.code81.LibraryManagementSystem.dto.request.SystemUserRequest;
import com.code81.LibraryManagementSystem.dto.response.SystemUserResponse;
import com.code81.LibraryManagementSystem.entity.Role;
import com.code81.LibraryManagementSystem.entity.SystemUser;
import com.code81.LibraryManagementSystem.repository.RoleRepository;
import com.code81.LibraryManagementSystem.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemUserService {

    private final SystemUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserActivityLogService userActivityLogService;

    // ================= CREATE USER =================
    public SystemUserResponse createUser(SystemUserRequest request) {
        SystemUser user = new SystemUser();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setStatus(request.status());

        // Encode password
        user.setPassword(passwordEncoder.encode(request.password()));

        // Set roles
        user.setRoles(getRolesFromIds(request.roleIds()));

        SystemUser savedUser = userRepository.save(user);

        userActivityLogService.saveLogAction("Creating new System user with username "+savedUser.getUsername()+"and Roles "+savedUser.getRoles());

        return mapToResponse(savedUser);
    }

    // ================= UPDATE USER =================
    @Transactional
    public SystemUserResponse updateUser(Integer userId, SystemUserRequest request) {
        SystemUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }
        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }
        if (request.status() != null) {
            user.setStatus(request.status());
        }
        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
        if (request.roleIds() != null) {
            user.setRoles(getRolesFromIds(request.roleIds()));
        }

        userActivityLogService.saveLogAction("Updating System user with username "+user.getUsername()+"and Roles "+user.getRoles());

        return mapToResponse(userRepository.save(user));
    }

    // ================= GET USER BY ID =================
    public SystemUserResponse getUserById(Integer id) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return mapToResponse(user);
    }

    // ================= GET ALL USERS =================
    public List<SystemUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ================= DELETE USER =================
    @Transactional
    public void deleteUser(Integer id) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        userRepository.delete(user);
        userActivityLogService.saveLogAction("Deleting System user with username "+user.getUsername()+"and Roles "+user.getRoles());
    }

    // ================= HELPER METHODS =================
    private Set<Role> getRolesFromIds(Set<Integer> roleIds) {
        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (Integer roleId : roleIds) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
        }
        return roles;
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

