package me.devk.practise.notesapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "notes")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    private String id;

    private String description;

    @Override
    public String toString() {
        return description;
    }
}
