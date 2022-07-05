package com.nobelti.rickandmorty.service;


import com.nobelti.rickandmorty.model.Role;

public interface RoleService {
    Role insertRole(Role role);
    Role findByRole(String role);
}
