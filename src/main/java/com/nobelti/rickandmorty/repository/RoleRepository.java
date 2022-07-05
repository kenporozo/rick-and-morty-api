package com.nobelti.rickandmorty.repository;


import com.nobelti.rickandmorty.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
