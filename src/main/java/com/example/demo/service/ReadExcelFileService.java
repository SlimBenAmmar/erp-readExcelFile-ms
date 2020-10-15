package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface ReadExcelFileService {

	public void saveComptesComptablesGenereux(MultipartFile file);

	public void deleteComptesComptablesGenereux();
}
