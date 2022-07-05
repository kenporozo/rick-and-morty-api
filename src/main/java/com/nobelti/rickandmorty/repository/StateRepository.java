package com.nobelti.rickandmorty.repository;

import com.nobelti.rickandmorty.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
    State findByState(String state);
}
