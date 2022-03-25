package com.example.demo.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Setter
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "otvc"), @UniqueConstraint(columnNames = "emailorphone")})
public class Otvc {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    @NotNull
    private String emailorphone;
    @NotNull

    private Date createdAt;
    @NotNull
    private String otvc;

}
