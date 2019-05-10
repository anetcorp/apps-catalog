package com.nvidia.server.catalog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utility.helper.DBConnection;

public class PartNumberService {
	private static Logger logger = LoggerFactory.getLogger(PartNumberService.class);
	private static final String INSERT_QUERY = "INSERT INTO part_numbers (nvidia_partner, server_name, nvidia_brand, architecture, max_gpu, workload, nvidia_partner_part_number, tesla_form_factor, active_passive, processor_type, max_cpu, server_form_factor, rack_unit, mfg_released) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String TRUNCATE_QUERY = "truncate part_numbers;";

	private PartNumberService() {
	}

	private static String getCellValue(Row currentRow, int index) {

		
		try {
			return Optional.ofNullable(currentRow.getCell(index)).isPresent()
					&& !currentRow.getCell(index).getStringCellValue().trim().isEmpty()
							? currentRow.getCell(index).getStringCellValue()
							: "";

		} catch (Exception e) {
			return Optional.ofNullable(currentRow.getCell(index)).isPresent()
					? String.valueOf(currentRow.getCell(index).getNumericCellValue())
					: "";
		}
	}

	private static double getNumericCellValue(Row currentRow, int index) {

		try {
			return Optional.ofNullable(currentRow.getCell(index)).isPresent()
					? currentRow.getCell(index).getNumericCellValue()
					: 0;
		} catch (Exception e) {
			try {
				return Optional.ofNullable(currentRow.getCell(index)).isPresent()
						? Double.parseDouble(currentRow.getCell(index).getStringCellValue())
						: 0;
			} catch (Exception e2) {
				return 0;
			}
		}
	}

	public static void postPartNumber() {
		try (FileInputStream excelFile = new FileInputStream(
				new File(Utility.getProperties().getProperty("server.catalog.file.path")));
				Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY);) {

			stmt.executeUpdate(TRUNCATE_QUERY);

			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			int index = 0;

			for (Iterator<Row> iterator = datatypeSheet.iterator(); iterator.hasNext();) {
				Row currentRow = iterator.next();

				if (!Optional.ofNullable(currentRow).isPresent()) {
					continue;
				}
				if (index > 0) {

					String partner = getCellValue(currentRow, 0);
					if (partner.isEmpty())
						break;
					String serverName = getCellValue(currentRow, 1);
					String brand = getCellValue(currentRow, 2);
					String architecture = getCellValue(currentRow, 3);
					Double maxGpu = getNumericCellValue(currentRow, 4);
					String workLoad = getCellValue(currentRow, 5);
					String partNumber = getCellValue(currentRow, 6);
					String teslaFormFactor = getCellValue(currentRow, 7);
					String activePassive = getCellValue(currentRow, 8);
					String processoreType = getCellValue(currentRow, 9);
					Double maxCpu = getNumericCellValue(currentRow, 10);
					String formFactor = getCellValue(currentRow, 11);
					String rackUnit = getCellValue(currentRow, 12);
					String mfgRelease = getCellValue(currentRow, 13);

					if (!serverName.trim().isEmpty() || !brand.trim().isEmpty() || !architecture.trim().isEmpty()
							|| !workLoad.trim().isEmpty()) {
						preparedStatement.setString(1, partner.trim());
						preparedStatement.setString(2, serverName.trim());
						preparedStatement.setString(3, brand.trim());
						preparedStatement.setString(4, architecture.trim());
						preparedStatement.setString(5, new DecimalFormat("#").format(maxGpu));
						preparedStatement.setString(6, workLoad.trim());
						preparedStatement.setString(7, partNumber.trim());
						preparedStatement.setString(8, teslaFormFactor.trim());
						preparedStatement.setString(9, activePassive.trim());
						preparedStatement.setString(10, processoreType.trim());
						preparedStatement.setString(11, new DecimalFormat("#").format(maxCpu));
						preparedStatement.setString(12, formFactor.trim());
						preparedStatement.setString(13, rackUnit.trim());
						preparedStatement.setString(14, mfgRelease.trim());
						preparedStatement.addBatch();
					}
				} else if (index == 0) {
					index++;
				}
			}
			preparedStatement.executeBatch();
			workbook.close();
			excelFile.close();
		} catch (IOException | SQLException e) {
			logger.error(Utility.getExceptionString(e));
		}
	}

}