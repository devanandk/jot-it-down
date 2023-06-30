package me.devk.practise.notesapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MinioConfiguration {
    
    @Value("${minio.host:localhost}")
    private String minioHost;

    @Value("${minio.port:9000}")
    private String minioPort;

    @Value("${minio.bucket:image-store}")
    private String minioBucket;

    @Value("${minio.root.user:mykey}")
    private String minioAccessKey;

    @Value("${minio.root.password:mysecret}")
    private String minioSecretKey;

    @Value("${minio.useSSL:false}")
    private boolean minioUseSSL;

    @Value("${minio.reconnect.enabled:false}")
    private boolean minioReconnectEnabled;
}
