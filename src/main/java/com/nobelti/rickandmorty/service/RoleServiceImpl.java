package com.nobelti.rickandmorty.service;

import com.nobelti.rickandmorty.model.Role;
import com.nobelti.rickandmorty.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    @Override
    public Role insertRole(Role role) {
        log.info("Saving new role {}", role.getRole());
        return roleRepository.saveAndFlush(role);
    }
    @Override
    public Role findByRole(String role) {
        log.info("Find by role = {}", role);
       return roleRepository.findByRole(role);
    }
}
