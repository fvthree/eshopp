package com.fvthree.eshop.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fvthree.eshop.auth.dto.JwtResponse;
import com.fvthree.eshop.auth.dto.LoginRequest;
import com.fvthree.eshop.auth.dto.SignupRequest;
import com.fvthree.eshop.auth.jwt.JwtUtils;
import com.fvthree.eshop.auth.models.ERole;
import com.fvthree.eshop.auth.models.Role;
import com.fvthree.eshop.auth.repository.RoleRepository;
import com.fvthree.eshop.auth.services.AuthService;
import com.fvthree.eshop.auth.services.UserDetailsImpl;
import com.fvthree.eshop.auth.services.UserDetailsServiceImpl;
import com.fvthree.eshop.users.User;
import com.fvthree.eshop.users.UserRepository;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    private static final String ROLE_NOT_FOUND_ERROR_MESSAGE = "Error: Role is not found.";
    
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
    
    @Autowired
    AuthService authUserService;
    
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(
        @Valid @RequestBody LoginRequest loginRequest)
    {
        return ResponseEntity.ok(authUserService
            .authenticateUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> registerUser(
        @Valid @RequestBody SignupRequest signUpRequest,
        @RequestParam(required = false, defaultValue = "false") boolean sendPasswordEmail) {
        if (userRepository.existsByEmail(signUpRequest.getEmail()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Error: Email is already in use!");
        }

        // Create new user's account
        User user = User.builder().apartment(signUpRequest.getApartment())
        		.city(signUpRequest.getCity())
        		.country(signUpRequest.getCountry())
        		.email(signUpRequest.getEmail())
        		.isActive(true)
				.isAdmin(false)
				.isNotLocked(true)
				.name(signUpRequest.getName())
				.password(encoder.encode(signUpRequest.getPassword()))
				.phone(signUpRequest.getPhone())
				.street(signUpRequest.getStreet())
				.zip(signUpRequest.getZip())
				.build();

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
            .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND_ERROR_MESSAGE));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(authUserService
            .authenticateUser(signUpRequest.getEmail(), signUpRequest.getPassword()));
    }

    /**
     * Refreshes the user's pair of tokens by generating a new set of
     * <code>accessToken</code> and
     * <code>refreshToken</code>, and invalidating the old pair.
     *
     * @param request an HTTP request.
     * @return a <code>JwtResponse</code> containing the new user information.
     */
    @GetMapping("/refresh")
    public JwtResponse refreshToken(
        HttpServletRequest request)
    {
        try
        {
            // Extract refresh token from request.
            String token = jwtUtils.parseJwt(request);
            String username = jwtUtils.getUserNameFromJwtToken(token);

            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                .loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            // Remove the old pair of tokens from the whitelist.
            jwtUtils.removeWhitelistJwtPair(token);

            // Generate a new set of tokens for the user.
            String accessToken = jwtUtils.generateJwtToken(authentication);
            String refreshToken = jwtUtils.generateJwtRefreshToken(authentication);

            // Whitelist the new pair of tokens.
            jwtUtils.whitelistJwtPair(accessToken, refreshToken);

            return new JwtResponse(accessToken,
                refreshToken,
                userDetails.getUserId(),
                userDetails.getEmail(),
                userDetails.getIsVerified(),
                roles);
        } catch (Exception e)
        {
            log.error("Failed to refresh the authentication token.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to refresh the authentication token. Try again later.");
        }
    }

    /**
     * Logs out the user by taking the token from the <code>Authorization</code>
     * header,
     * extracting the username from its body, and invalidating all of the tokens
     * that
     * belong to that user.
     *
     * @param request an HTTP request.
     * @return a <code>Response Entity</code> with no body.
     */
    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request)
    {
        try
        {
            // Extract refresh token from request.
            String token = jwtUtils.parseJwt(request);
            String user = jwtUtils.getUserNameFromJwtToken(token);

            // Remove all User's tokens from the whitelist
            jwtUtils.removeWhitelistUser(user);
        } catch (Exception e)
        {
            log.error("Failed to logout.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to logout. Try again later.");
        }
    }

}
