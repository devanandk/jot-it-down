// package me.devk.practise.notesapp.config;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

// @Configuration
// public class MongoConfiguration extends AbstractMongoClientConfiguration {

//     @Value("${spring.data.mongodb.uri}")
//     private String uri;

//     @Value("${spring.data.mongodb.database}")
//     private String db;

//     @Override
//     protected String getDatabaseName() {
//         return db;
//     }
// }