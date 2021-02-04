package com.microseladherent.mapper.impl;

import org.springframework.stereotype.Service;

import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;
import com.microseladherent.mapper.IUserMapper;

@Service
public class UserMapperImpl implements IUserMapper{
	
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
