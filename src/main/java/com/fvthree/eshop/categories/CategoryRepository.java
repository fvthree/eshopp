package com.fvthree.eshop.categories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByCategoryId(UUID uuid);
	Optional<Category> findByName(String name);
}
