package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 60)
    private String name;

    public Roles(String name) {
        this.name = name;
    }
}
