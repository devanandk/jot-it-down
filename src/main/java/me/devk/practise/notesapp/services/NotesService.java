package me.devk.practise.notesapp.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.extern.slf4j.Slf4j;
import me.devk.practise.notesapp.models.Note;
import me.devk.practise.notesapp.repositories.NotesRepository;

@Service
@Slf4j
public class NotesService {
    
    @Autowired
    private NotesRepository notesRepository;

    // @Autowired
    // private AppProperties properties;

    @Autowired
    private MinioService minioService;

    private Parser parser = Parser.builder().build();

    private HtmlRenderer renderer = HtmlRenderer.builder().build();

    public void getAllNotes(Model model) {
        List<Note> notes = notesRepository.findAll();

        Collections.reverse(notes);

        model.addAttribute("notes", notes);
    }

    public void saveNote(String description, Model model) {
        if (description != null && !description.trim().isEmpty()) {
            log.info("Saving Note: {}", description);
            Node document = parser.parse(description.trim());
            notesRepository.save(new Note(null, renderer.render(document)));
            model.addAttribute("description", "");
        }
    }

    public void uploadImage(MultipartFile imageFile, String description, Model model) throws IllegalStateException, IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException {
        String fileId = minioService.saveImageToBucket(imageFile);
        model.addAttribute("description", description + " ![](/img/" + fileId + ")");
    }

    public byte[] getImageByName(String name) throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException {
        InputStream imageStream = minioService.getImageByName(name);
        return IOUtils.toByteArray(imageStream);
    }
}
