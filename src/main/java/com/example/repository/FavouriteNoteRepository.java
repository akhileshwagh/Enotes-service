package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.FavouriteNotes;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNotes, Integer> {

	List<FavouriteNotes> findByUserId(int userId);

}