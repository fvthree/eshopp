package com.fvthree.eshop.users;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) throws JsonProcessingException {
		return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
	}
	
    @GetMapping("/users")
    public UsersResponse getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) 
            		throws JsonProcessingException {
    	
        log.info(" size ::: " + pageSize + ", number ::: " + pageNo + ", sortBy ::: " + sortBy + ", sortDir ::: " + sortDir);
        
        return userService.getAllUser(pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto UserDto, 
    		@PathVariable(name = "id") UUID id) throws JsonProcessingException {

       return new ResponseEntity<>(userService.updateUser(UserDto, id), HttpStatus.OK);
    }
    
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") UUID id) {

        userService.deleteUser(id);

        return new ResponseEntity<>("User entity deleted successfully.", HttpStatus.OK);
    }
    
	@PostMapping("/user/get/count")
	public ResponseEntity<?> getUserCount() throws JsonProcessingException {
		return new ResponseEntity<>(userService.getUsersCount(), HttpStatus.OK);
	}
	
}
