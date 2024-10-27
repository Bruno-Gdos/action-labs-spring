package br.com.actionlabs.carboncalc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("users")
public class User {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true) 
    private String email;    
    private String uf;
    private String phoneNumber;

}
