package me.devk.practise.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import me.devk.practise.notesapp.repositories.NotesRepository;

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackageClasses = NotesRepository.class)
public class NotesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesAppApplication.class, args);
	}

	
}
