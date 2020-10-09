package com.example.demo.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	public List<CompteComptable> getAllCompteComptablesGenereux() {
		logger.info("getAllCompteComptablesGenereux : Erp-Finance-MS ");
		// Delete all public comptes comptables
		readExcelFileRepository.deleteAll(readExcelFileRepository.findByUuidEntreprise(null));
		try {
			FileInputStream excelFile = new FileInputStream("fe");
			Workbook workbook = new XSSFWorkbook(excelFile);
			List<CompteComptable> lstComptesComptables = new ArrayList<CompteComptable>();
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();

				int rowNumber = 0;
				while (rows.hasNext()) {
					Row currentRow = rows.next();

					// skip header
					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					Iterator<Cell> cellsInRow = currentRow.iterator();

					CompteComptable compteComptable = new CompteComptable();

					int cellIndex = 0;
					while (cellsInRow.hasNext()) {
						Cell currentCell = cellsInRow.next();

						if (cellIndex == 0) { // Numero
							compteComptable.setNumero(String.valueOf((int) currentCell.getNumericCellValue()));
						} else if (cellIndex == 1) { // Description
							compteComptable.setDescription(currentCell.getStringCellValue());
						}
						// Classe compte
						compteComptable.setClasse(workbook.getSheetName(i));
						cellIndex++;
					}

					lstComptesComptables.add(compteComptable);
				}

				// Close WorkBook
				workbook.close();
			}
			readExcelFileRepository.saveAll(lstComptesComptables);
			return lstComptesComptables;
		} catch (IOException e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}

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
