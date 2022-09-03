package com.fvthree.eshop.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fvthree.eshop.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User save(UserDto userDto) throws JsonProcessingException {

		User user = User.builder().apartment(userDto.getApartment()).city(userDto.getCity()).country(userDto.getCountry()).email(userDto.getEmail()).isActive(true)
				.isAdmin(false).isNotLocked(true).name(userDto.getName()).password(userDto.getPassword()).phone(userDto.getPhone()).street(userDto.getStreet())
				.zip(userDto.getZip()).build();
		
		return userRepository.save(user);
		
	}

	@Override
	public UsersResponse getAllUser(int pageNo, int pageSize, String sortBy, String sortDir)
			throws JsonProcessingException {
		
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
        Page<User> Users = userRepository.findAll(pageable);
        
        List<User> listOfUsers = Users.getContent();

		UsersResponse UserResponse = UsersResponse.builder()
				.content(listOfUsers)
				.pageNo(Users.getNumber())
				.pageSize(Users.getSize())
				.totalElements(Users.getTotalElements())
				.totalPages(Users.getTotalPages())
				.last(Users.isLast())
				.build();
		
        return UserResponse;
	}

	@Override
	public User getUserById(UUID id) {
		log.info("[getUserById] id ::: " + id );
		User user = userRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return user;
	}

	@Override
	public User updateUser(UserDto userDto, UUID id) throws JsonProcessingException {
		
		User user = userRepository.findByUserId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setPhone(userDto.getPhone());
		user.setIsAdmin(userDto.getIsAdmin());
		user.setStreet(userDto.getStreet());
		user.setApartment(userDto.getApartment());
		user.setZip(userDto.getZip());
		user.setCity(userDto.getCity());
		user.setCountry(userDto.getCountry());
		user.setActive(true);
		
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(UUID id) {
		
		log.info("[deleteUser] id ::: " + id);
		User user = userRepository.findByUserId(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		
		userRepository.delete(user);
	}

	@Override
	public UserCountResponse getUsersCount() {
		UserCountResponse userCount = UserCountResponse.builder()
				.userCount(userRepository.count())
				.build();
		return userCount;
	}
	
	
	
}
