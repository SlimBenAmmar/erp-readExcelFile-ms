package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.CompteComptable;

public interface ReadExcelFileRepository extends MongoRepository<CompteComptable, String> {

	public List<CompteComptable> findByUuidEntreprise(String uuidEntreprise);
	
}
	