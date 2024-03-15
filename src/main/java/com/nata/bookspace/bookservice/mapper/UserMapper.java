package com.nata.bookspace.bookservice.mapper;

import com.nata.bookspace.bookservice.dto.ResponseUserDTO;
import com.nata.bookspace.bookservice.entity.User;


public class UserMapper {

    public static ResponseUserDTO mapToUserDTO(User user) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO(
                user.getId(),
                user.getEmail()
        );

        return responseUserDTO;
    }

}
