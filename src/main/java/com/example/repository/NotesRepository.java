package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>  {

}
