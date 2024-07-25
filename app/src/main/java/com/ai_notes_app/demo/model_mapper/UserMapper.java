package com.ai_notes_app.demo.model_mapper;

import com.ai_notes_app.demo.model.User;
import com.ai_notes_app.demo.controller.dto.UserDTO;

public interface UserMapper {

    UserDTO toUserDTO(User user);
}
