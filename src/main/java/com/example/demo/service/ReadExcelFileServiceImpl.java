package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CompteComptable;
import com.example.demo.repository.ReadExcelFileRepository;
import com.example.demo.util.ExcelHelper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReadExcelFileServiceImpl implements ReadExcelFileService {

	private final static Logger logger = Logger.getLogger(ReadExcelFileServiceImpl.class);

	@Autowired
	ReadExcelFileRepository readExcelFileRepository;

	@Override
	public void saveComptesComptablesGenereux(MultipartFile file) {
		try {
			List<CompteComptable> ComptesComptables = ExcelHelper.readExcelFile(file.getInputStream());
			readExcelFileRepository.saveAll(ComptesComptables);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	@Override
	public void deleteComptesComptablesGenereux() {
		readExcelFileRepository.deleteAll(readExcelFileRepository.findByUuidEntreprise(null));
	}

}
