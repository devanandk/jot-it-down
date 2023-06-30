package me.devk.practise.notesapp.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.devk.practise.notesapp.config.MinioConfiguration;

@Service
@EnableConfigurationProperties(MinioConfiguration.class)
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MinioService {
    
    private MinioClient client;

    @Autowired
    private MinioConfiguration properties;

    @PostConstruct
    public void init() throws MalformedURLException {
        initializeMinioClient();
    }

    private void initializeMinioClient() throws MalformedURLException {
        boolean success = false;
        URL url = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host(properties.getMinioHost())
            .port(properties.getMinioPort())
            .build().toUri().toURL();
        log.info("Trying to initialize connection to Minio Object Store on : {}", url);
        while (!success) {
            try {
                client = MinioClient.builder()
                            .endpoint(url)
                            .credentials(
                                properties.getMinioAccessKey(), 
                                properties.getMinioSecretKey()
                            ).build();
                
                BucketExistsArgs args = BucketExistsArgs.builder().bucket(properties.getMinioBucket()).build();
                if (!client.bucketExists(args)) {
                    client.makeBucket(
                        MakeBucketArgs
                            .builder()
                            .bucket(properties.getMinioBucket())
                            .build()
                        );
                } else {
                    log.info("> Minio bucket already exists.");
                }
                success = true;
            } catch (Exception e) {
                log.error("Minio Connection initialization failed: {}", e.getMessage());
                log.info("> Minio Retry Connection?: {}", properties.isMinioReconnectEnabled());
                if (properties.isMinioReconnectEnabled()) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        log.error("Interruped while waiting before Minio Reconnect attempt", ex);
                    }
                } else {
                    success = true;
                }
            }
        }
    }

    public String saveImageToBucket(MultipartFile imageFile) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        String fileId = UUID.randomUUID().toString() + "." 
                        + FilenameUtils.getExtension(imageFile.getOriginalFilename());
        InputStream inSt = imageFile.getInputStream();
        PutObjectArgs args = PutObjectArgs.builder()
                                .bucket(properties.getMinioBucket())
                                .stream(inSt, inSt.available(), -1)
                                .contentType(imageFile.getContentType())
                                .object(fileId)
                                .build();

        client.putObject(args);
        inSt.close();
        return fileId;
    }

    public InputStream getImageByName(String name) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
        GetObjectArgs args = GetObjectArgs.builder()
                                .bucket(properties.getMinioBucket())
                                .object(name)
                                .build();
        return client.getObject(args);
    }

}
