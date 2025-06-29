package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {

}
