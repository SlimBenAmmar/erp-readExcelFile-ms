package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CompteComptable;
import com.example.demo.service.ReadExcelFileService;
import com.example.demo.util.ExcelHelper;
import com.example.demo.util.ResponseMessage;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/finance/comptecomptable")
public class ReadExcelFileController {

	@Autowired
	ReadExcelFileService readExcelFileService;

	@GetMapping()
	public List<CompteComptable> getAllCompteComptablesGenereux(@RequestParam("file") MultipartFile file) {
		return readExcelFileService.getAllCompteComptablesGenereux();
	}

	@PostMapping("/upload")	
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				readExcelFileService.deleteComptesComptablesGenereux();
				readExcelFileService.saveComptesComptablesGenereux(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}
}
