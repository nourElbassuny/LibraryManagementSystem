package com.code81.LibraryManagementSystem.util;

import com.code81.LibraryManagementSystem.entity.SystemUser;
import com.code81.LibraryManagementSystem.repository.UserActivityLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


public class ActivityLogHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static String toJson(Map<String,Object>data){
        try {
            return mapper.writeValueAsString(data);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error generating JSON log", e);
        }
    }
}
