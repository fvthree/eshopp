package com.fvthree.eshop.users;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserDto {
	
	private UUID user_id;
	
	private String name;
    
    @NotEmpty
    private String email;
    
    @NotEmpty
	private String password;

    private String phone;
    
    private Boolean isAdmin;
    
    private String street;
    
    private String apartment;
    
    private String zip;

    private String city;
    
    private String country;
    
}
