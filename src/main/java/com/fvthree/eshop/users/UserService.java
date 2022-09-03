package com.fvthree.eshop.users;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserService {
	User save(UserDto UserDto) throws JsonProcessingException;
	UsersResponse getAllUser(int pageNo, int pageSize, String sortBy, String sortDir) throws JsonProcessingException;
	User getUserById(UUID id);
	User updateUser(UserDto userDto, UUID id) throws JsonProcessingException;
	void deleteUser(UUID id);
	UserCountResponse getUsersCount();
}
