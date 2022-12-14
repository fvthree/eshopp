package com.fvthree.eshop.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(UUID userId);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
}
