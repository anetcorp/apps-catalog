package com.nvidia.resources.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class POIUtils {

	public ByteArrayOutputStream exportExcel(String[] headers, Object[][] data) throws IOException {
		ByteArrayOutputStream os;
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("sheet1");

		this.createHeaderRow((Sheet) sheet, headers);
		POIUtils.fillExcel(data, sheet);

		for (int index = 0; index < 11; index++)
			sheet.autoSizeColumn(index);

		os = new ByteArrayOutputStream();
		try {
			workbook.write((OutputStream) os);
		} finally {
			workbook.close();
		}
		return os;
	}

	private static void fillExcel(Object[][] data, XSSFSheet sheet) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		addCellBorder(style);
		int rowCount = 0;
		for (Object[] gpu : data) {
			XSSFRow row = sheet.createRow(++rowCount);
			int columnCount = 0;
			for (Object field : gpu) {
				Cell cell = row.createCell(columnCount++);
				cell.setCellStyle(style);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (!(field instanceof Integer)) {
					// nothing to do
				} else {
					cell.setCellValue((double) ((Integer) field).intValue());
				}
			}
		}
	}

	static void addCellBorder(CellStyle style) {
		short borderType = HSSFCellStyle.BORDER_THIN;
		style.setBorderBottom(borderType);
		style.setBorderTop(borderType);
		style.setBorderRight(borderType);
		style.setBorderLeft(borderType);
		style.setAlignment(CellStyle.ALIGN_LEFT);
	}

	
	void addCellValue(Row row, String value, int index, Sheet sheet) {
		CellStyle style = sheet.getWorkbook().createCellStyle();
		addCellBorder(style);
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		
		if(row.getRowNum() == 0) {
			font.setColor(IndexedColors.GREEN.getIndex());
		}
		
		style.setFont(font);
		Cell cell = row.createCell(index);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

	void createHeaderRow(Sheet sheet, String[] headers) {
		Row row = sheet.createRow(0);
		int[] count = new int[] { 0 };
		Arrays.stream(headers).forEach(obj -> {
			int[] arrn = count;
			int n = arrn[0];
			arrn[0] = n + 1;
			this.addCellValue(row, obj, n, sheet);
		});
	}
}
