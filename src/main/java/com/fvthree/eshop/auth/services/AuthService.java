package com.fvthree.eshop.auth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fvthree.eshop.auth.dto.JwtResponse;
import com.fvthree.eshop.auth.jwt.JwtUtils;
import com.fvthree.eshop.auth.repository.RoleRepository;
import com.fvthree.eshop.exceptions.APIException;
import com.fvthree.eshop.users.User;
import com.fvthree.eshop.users.UserRepository;

@Service
public class AuthService {
	
	@Autowired
    AuthenticationManager authenticationManager;
    
	@Autowired
    UserRepository userRepository;
    
	@Autowired
    RoleRepository roleRepository;
    
	@Autowired
    PasswordEncoder encoder;
    
	@Autowired
    JwtUtils jwtUtils;

    public JwtResponse authenticateUser(final String email, final String password) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email,
                password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String jwtRefresh = jwtUtils.generateJwtRefreshToken(authentication);

        jwtUtils.whitelistJwtPair(jwt, jwtRefresh);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return new JwtResponse(jwt,
	            jwtRefresh,
	            userDetails.getUserId(),
	            userDetails.getEmail(),
	            userDetails.getIsVerified(),
	            roles);
    }

    public User getCurrentlyAuthenticatedUser() {
    	UserDetailsImpl userDetails =
            (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public String encodePassword(String password)
    {
        return encoder.encode(password);
    }
}
