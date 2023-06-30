// package me.devk.practise.notesapp.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.servlet.resource.PathResourceResolver;

// @Configuration
// @EnableConfigurationProperties(AppProperties.class)
// public class AppExternalResoureConfig implements WebMvcConfigurer {
    
//     @Autowired
//     private AppProperties properties;

//     @Override
//     public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         registry
//         .addResourceHandler("/uploads/**")
//         .addResourceLocations("file:" + properties.getUploadDir())
//         .setCachePeriod(3600)
//         .resourceChain(true)
//         .addResolver(new PathResourceResolver());
//     }
// }
