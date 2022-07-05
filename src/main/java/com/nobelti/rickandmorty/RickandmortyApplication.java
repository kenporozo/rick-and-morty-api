package com.nobelti.rickandmorty;

import com.nobelti.rickandmorty.model.*;
import com.nobelti.rickandmorty.service.CharacterService;
import com.nobelti.rickandmorty.service.RoleService;
import com.nobelti.rickandmorty.service.StateService;
import com.nobelti.rickandmorty.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class RickandmortyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RickandmortyApplication.class, args);
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService, StateService stateService, CharacterService characterService) {
		return args -> {
			stateService.insertState(new State(null, "ACTIVATED"));
			stateService.insertState(new State(null, "INACTIVATED"));

			roleService.insertRole(new Role(null, "ROLE_USER"));
			roleService.insertRole(new Role(null, "ROLE_ADMIN"));

			Role roleUser = roleService.findByRole("ROLE_USER");
			Role roleAdmin = roleService.findByRole("ROLE_ADMIN");

			userService.insertUser(new User(null,
					"barbara@mail.com",
					"1234",
					null,
					List.of(roleUser)
			));

			userService.insertUser(new User(null,
					"jonayker@mail.com",
					"1234",
					null,
					List.of(roleUser,roleAdmin)
			));
			characterService.insertCharacter(new CharacterEntity(null,
					"Rick Sanchez",
					Status.ALIVE,
					Gender.MALE,
					"https://rickandmortyapi.com/api/character/avatar/1.jpeg",
					null));
			characterService.insertCharacter(new CharacterEntity(null,
					"Morty Smith",
					Status.ALIVE,
					Gender.MALE,
					"https://rickandmortyapi.com/api/character/avatar/1.jpeg",
					null));

		};
	}

}
