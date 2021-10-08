package com.example.jwtt.dao;

import com.example.jwtt.enitity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
	UserRole findByRoleId(String roleId);
}
