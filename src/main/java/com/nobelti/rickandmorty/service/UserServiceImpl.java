package com.nobelti.rickandmorty.service;

import com.nobelti.rickandmorty.model.User;
import com.nobelti.rickandmorty.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import static com.nobelti.rickandmorty.RickandmortyApplication.passwordEncoder;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final StateServiceImpl stateService;
    private final RoleServiceImpl roleService;
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public User insertUser(User user){
        user.setState(stateService.findByState("ACTIVATED"));
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userLogin = userRepository.findByEmail(email);
        if(userLogin == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else if(userLogin.getState().getIdState() != 1){
            log.error("User {} inactivated", userLogin.getEmail());
            throw new UsernameNotFoundException("User inactivated");
        }
        else{
            log.info("User found in the database: {}", email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            userLogin.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
            });
            return new org.springframework.security.core.userdetails.User(userLogin.getEmail(), userLogin.getPassword(), authorities);
        }
    }

}
