package com.microseladherent.mapper;

import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;

public interface IUserMapper {
	
	UserDTO userTouserDTO(User entity);
	
	User userDTOToUser(UserDTO dto);

}
