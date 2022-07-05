package com.nobelti.rickandmorty.service;


import com.nobelti.rickandmorty.model.State;

public interface StateService {
    State insertState(State state);
    State findByState(String state);
}
