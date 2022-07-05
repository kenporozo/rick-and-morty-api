package com.nobelti.rickandmorty.service;

import com.nobelti.rickandmorty.exceptions.InvalidRequestException;
import com.nobelti.rickandmorty.model.CharacterEntity;
import com.nobelti.rickandmorty.model.Gender;
import com.nobelti.rickandmorty.model.Status;
import com.nobelti.rickandmorty.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService{
    private final StateServiceImpl stateService;
    private final CharacterRepository characterRepository;

    @Override
    public List<CharacterEntity> getCharacters(String gender, String status) {
        int i = (gender != null ? 1 : 0) | (status != null ? 2 : 0);
        switch (i) {
            case 0:
                return characterRepository.findAll();
            case 1:
                return characterRepository.findByGender(Gender.valueOf(gender.toUpperCase()));
            case 2:
                return characterRepository.findByStatus(Status.valueOf(status.toUpperCase()));
            case 3:
                return characterRepository.findByGenderAndStatus(Gender.valueOf(gender.toUpperCase()), Status.valueOf(status.toUpperCase()));
            default:
                return null;
        }
    }

    @Override
    public List<CharacterEntity> getCharactersById(List<Long> ids) throws InvalidRequestException {
        List<CharacterEntity> list = new ArrayList<>();
        ids.forEach(id -> {
            if (characterRepository.findById(id).isPresent()) {
                list.add(characterRepository.findById(id).get());
            }
        });
        if(list.size() == 0){
            message("No se han encontrado personajes con los ids ingresados");
        }
        return list;
    }

    @Override
    public CharacterEntity insertCharacter(CharacterEntity character) throws InvalidRequestException {
        validateBody(character);
        character.setState(stateService.findByState("ACTIVATED"));
        return characterRepository.save(character);
    }

    @Override
    public void updateCharacter(CharacterEntity character) throws InvalidRequestException {
        if (characterRepository.findById(character.getIdCharacter()).isPresent()) {
            //Validaciones
            validateBody(character);
            CharacterEntity characterUpdate = characterRepository.findById(character.getIdCharacter()).get();
            characterUpdate.setName(character.getName());
            characterUpdate.setStatus(character.getStatus());
            characterUpdate.setGender(character.getGender());
            characterUpdate.setImage(character.getImage());
            characterRepository.save(characterUpdate);
        }else {
            message("El personaje que buscas no existe");
        }
    }

    @Override
    public void deleteCharacter(Long id) throws InvalidRequestException {
        if(characterRepository.findById(id).isPresent()){
            characterRepository.deleteCharacterById(id);
        }else {
            message("El personaje que buscas no existe");
        }
    }

    private void message(String msgRsp) throws InvalidRequestException{
        throw new InvalidRequestException(msgRsp);
    }

    private void validateBody(CharacterEntity character) throws InvalidRequestException {
        if(character.getName() == null){
            message("Nombre de personaje obligatorio");
        }
        else if(character.getGender() == null){
            message("El genero ingresado no se encuntra entre los correctos (MALE, FEMALE ó UNKNOW)");
        }
        else if(character.getStatus() == null){
            message("El status ingresado no se encuntra entre los correctos (ALIVE, DEAD ó UNKNOW)");
        }
        else if(character.getImage() == null){
            message("La imagen es obligatoria");
        }
    }
}
