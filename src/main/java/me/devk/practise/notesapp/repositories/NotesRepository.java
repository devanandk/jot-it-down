package me.devk.practise.notesapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import me.devk.practise.notesapp.models.Note;

public interface NotesRepository extends MongoRepository<Note, String> {
    
}
