package com.fvthree.eshop.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupRequest {
	
    @NotBlank
    @Size(max = 254)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
	private String name;
	
    private String phone;
    
    private Boolean isAdmin;
    
    private String street;
    
    private String apartment;
    
    private String zip;

    private String city;
    
    private String country;
}
