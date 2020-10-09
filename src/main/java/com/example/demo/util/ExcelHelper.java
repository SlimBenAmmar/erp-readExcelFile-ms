package com.example.demo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.CompteComptable;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<CompteComptable> readExcelFile(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);
			List<CompteComptable> ComptesComptables = new ArrayList<CompteComptable>();
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

					int cellIdx = 0;
					while (cellsInRow.hasNext()) {
						Cell currentCell = cellsInRow.next();

						switch (cellIdx) {
						case 0:
							compteComptable.setNumero(String.valueOf((int) currentCell.getNumericCellValue()));
							break;

						case 1:
							compteComptable.setDescription(currentCell.getStringCellValue());
							break;

						default:
							break;
						}
						compteComptable.setClasse(workbook.getSheetName(i));
						cellIdx++;
					}

					ComptesComptables.add(compteComptable);
				}

				workbook.close();
			}
			return ComptesComptables;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
