package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CompteComptable;

public interface ReadExcelFileService {

	public List<CompteComptable> getAllCompteComptablesGenereux();

	public void saveComptesComptablesGenereux(MultipartFile file);

	public void deleteComptesComptablesGenereux();
}
