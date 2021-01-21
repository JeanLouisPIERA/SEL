package com.microseladherent.dto;

import org.springframework.stereotype.Component;

import com.microseladherent.entities.User;

@Component
public class UserMapperImpl{

    public UserDTO userTouserDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setPassword( entity.getPassword() );
        userDTO.setAdresseMail( entity.getEmail() );
        userDTO.setUsername( entity.getUsername() );
        userDTO.setPasswordConfirm( entity.getPasswordConfirm() );
        
        return userDTO;
    }

    public User userDTOToUser(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setPassword( dto.getPassword() );
        user.setEmail( dto.getAdresseMail() );
        user.setPasswordConfirm( dto.getPasswordConfirm() );

        return user;
    }
}