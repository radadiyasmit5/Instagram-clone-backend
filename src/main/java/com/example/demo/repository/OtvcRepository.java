package com.example.demo.repository;

import com.example.demo.entity.Otvc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OtvcRepository extends JpaRepository<Otvc, Long> {
    public Optional<Otvc> findByemailorphone(String emailorphone);


    public Optional<Boolean> existsByemailorphone(String emailorphone);


}
