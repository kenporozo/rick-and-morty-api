package com.nobelti.rickandmorty.service;

import com.nobelti.rickandmorty.exceptions.InvalidRequestException;
import com.nobelti.rickandmorty.model.CharacterEntity;

import java.util.List;

public interface CharacterService {
    List<CharacterEntity> getCharacters(String gender, String status);
    List<CharacterEntity> getCharactersById(List<Long> ids) throws InvalidRequestException;
    CharacterEntity insertCharacter(CharacterEntity character) throws InvalidRequestException;
    void updateCharacter(CharacterEntity character) throws InvalidRequestException;
    void deleteCharacter(Long id) throws InvalidRequestException;
}
