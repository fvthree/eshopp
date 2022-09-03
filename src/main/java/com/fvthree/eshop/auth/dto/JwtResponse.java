package com.fvthree.eshop.auth.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class JwtResponse {

	private final List<String> roles;

	private String accessToken;

	private String refreshToken;

	private String tokenType = "Bearer";

	private UUID userId;

	private String email;

	private boolean isVerified;

	public JwtResponse(String accessToken, String refreshToken, UUID userId, String email, boolean isVerified,
			List<String> roles) {
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
		this.userId = userId;
		this.email = email;
		this.roles = roles;
		this.isVerified = isVerified;
	}
}
