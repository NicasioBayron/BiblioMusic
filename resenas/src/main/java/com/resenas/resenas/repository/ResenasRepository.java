package com.resenas.resenas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resenas.resenas.model.Resenas;

public interface ResenasRepository extends JpaRepository<Resenas, Long> {
}