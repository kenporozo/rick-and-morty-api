package com.nobelti.rickandmorty.repository;

import com.nobelti.rickandmorty.model.CharacterEntity;
import com.nobelti.rickandmorty.model.Gender;
import com.nobelti.rickandmorty.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    List<CharacterEntity> findByStatus(Status status);
    List<CharacterEntity> findByGender(Gender gender);
    List<CharacterEntity> findByGenderAndStatus(Gender gender, Status status);
    @Modifying
    @Query(
            value = "UPDATE CharacterEntity c SET c.state = 2 WHERE c.idCharacter = :id"
    )
    void deleteCharacterById(Long id);
}
