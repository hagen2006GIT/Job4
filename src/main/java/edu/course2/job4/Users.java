package edu.course2.job4;

import lombok.Getter;
import lombok.Setter;

// Data Transfer Object (DTO)
@Setter @Getter public class Users {
    private int id;
    private String username;
    private String fio;
    public Users(int id,String name,String fio) {
        this.id=id;
        this.username=name;
        this.fio=fio;
    }
}
