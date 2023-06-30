package me.devk.practise.notesapp.controllers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import me.devk.practise.notesapp.services.NotesService;

@Controller
public class NotesController {
    
    @Autowired
    private NotesService notesService;

    private static final String INDEX = "index";

    @GetMapping("/")
    public String index(Model model) {
        notesService.getAllNotes(model);
        return INDEX;
    }

    @PostMapping("/note")
    public String savenotes(
        @RequestParam("image") MultipartFile imageFile,
        @RequestParam String description,
        @RequestParam(required = false) String publish,
        @RequestParam(required = false) String upload,
        Model model
    ) throws IllegalStateException, IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException {
        if (publish != null && publish.equals("Publish")) {
            notesService.saveNote(description, model);
            notesService.getAllNotes(model);
            return "redirect:/";
        }
        
        if (upload != null && upload.equals("Upload")) {
            if (imageFile != null && imageFile.getOriginalFilename() != null
            && imageFile.getSize() > 0) {
                notesService.uploadImage(imageFile, description, model);
            }
            notesService.getAllNotes(model);
            return INDEX;
        }

        return INDEX;
    }

    @GetMapping(value = "/img/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImageByName(@PathVariable String name) throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException {
        return notesService.getImageByName(name);
    }
}
