package com.nobelti.rickandmorty.repository;

import com.nobelti.rickandmorty.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
