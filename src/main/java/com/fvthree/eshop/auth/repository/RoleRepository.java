package com.fvthree.eshop.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fvthree.eshop.auth.models.ERole;
import com.fvthree.eshop.auth.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
	Optional<Role> findByName(ERole name);
}
