package com.ai_notes_app.demo.model_mapper;

import com.ai_notes_app.demo.model.User;
import com.ai_notes_app.demo.controller.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole());
    }
}