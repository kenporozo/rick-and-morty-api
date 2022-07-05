package com.nobelti.rickandmorty.controller;

import com.nobelti.rickandmorty.exceptions.InvalidRequestException;
import com.nobelti.rickandmorty.model.CharacterEntity;
import com.nobelti.rickandmorty.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterEntity>> getUsers(@RequestParam(name = "gender",required = false) String gender,
                                                          @RequestParam(name = "status", required = false) String status){
        return ResponseEntity.ok(characterService.getCharacters(gender, status));
    }

    @GetMapping("/characters/{ids}")
    public ResponseEntity<List<CharacterEntity>> getUsersById(@PathVariable(name = "ids") List<Long> ids) throws InvalidRequestException {
        return ResponseEntity.ok(characterService.getCharactersById(ids));
    }

    @PostMapping("/characters")
    public ResponseEntity<CharacterEntity> insertUser(@RequestBody CharacterEntity character) throws InvalidRequestException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/characters").toUriString());
        return ResponseEntity.created(uri).body(characterService.insertCharacter(character));
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<?> updateUser(@RequestBody CharacterEntity character, @PathVariable(name = "id") Long id) throws InvalidRequestException{
        character.setIdCharacter(id);
        characterService.updateCharacter(character);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) throws InvalidRequestException {
        characterService.deleteCharacter(id);
        return ResponseEntity.ok().build();
    }
}

