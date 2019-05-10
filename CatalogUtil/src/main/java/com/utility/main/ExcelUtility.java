package com.utility.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nvidia.server.catalog.PartNumberService;
import com.utility.helper.BeanHelper;
import com.utility.helper.DBConnection;
import com.utility.helper.Utility;

/**
 * Utility to read excel file and insert data into DB
 * 
 * @author VINOD KUMAR KASHYAP
 * @since 12th Oct' 2017
 */
public class ExcelUtility {

	private static Logger logger = LoggerFactory.getLogger(ExcelUtility.class);

	/**
	 * Main method to be executed for this utility.
	 * 
	 * @param fileName
	 *            name of the file to be read
	 */
	public void readXLSXFile(String fileName) {
		logger.info("Reading Excel: " + fileName);
		XSSFWorkbook workbook = null;
		try (InputStream XlsxFileToRead = new FileInputStream(fileName)) {

			// Getting the workbook instance for xlsx file
			workbook = new XSSFWorkbook(XlsxFileToRead);
		} catch (FileNotFoundException e) {
			logger.error(com.nvidia.server.catalog.Utility.getExceptionString(e));
		} catch (IOException e) {
			logger.error(com.nvidia.server.catalog.Utility.getExceptionString(e));
		}

		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;
		BeanHelper bh;
		String value;
		String attribute = null;
		int index;
		Connection conn;
		Map<String, String> mapAttributeValues;

		conn = DBConnection.getConnection();

		// Iterating all the rows in the sheet
		Iterator<?> rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			if (row.getRowNum() == 0) {
				continue;
			}

			// Iterating all the cells of the current row
			Iterator<?> cells = row.cellIterator();

			// column 0 = name
			// column 1 = GPU Catalog Category
			// column 2 = Company Name
			// column 3 = GPU Scaling
			// column 4 = Product Description
			// column 5 = Supported Features
			// column 6 = URL to High Value Developer Page
			// column 7 = product
			// column 8 = category
			// column 9 = URL to NVIDIA Page (Such as Quickstart Guide)
			// column 10 = Featured on Main Page?

			bh = new BeanHelper();
			mapAttributeValues = new HashMap<>();

			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				value = getCellValue(cell);
				index = cell.getColumnIndex();
				switch (index) {
				case 0:
					bh.setName(value);
					attribute = Utility.NAME;
					break;
				case 1:
					bh.setGpuCatalogCategory(value);
					attribute = Utility.GPU_CATALOG_CATEGORY;
					break;
				case 2:
					bh.setCompanyName(value);
					attribute = Utility.COMPANY_NAME;
					break;
				case 3:
					bh.setGpuScaling(value);
					attribute = Utility.GPU_SCALING;
					break;
				case 4:
					bh.setDescription(value);
					attribute = Utility.PRODUCT_DESCRIPTION;
					break;
				case 5:
					bh.setFeatures(value);
					attribute = Utility.SUPPORTED_FEATURES;
					break;
				case 6:
					bh.setUrlToHigh(value);
					attribute = Utility.URL_TO_HIGH;
					break;
				case 7:
					bh.setIndustryCategory(value);
					attribute = Utility.INDUSTRY_CATEGORY;
					break;
				case 8:
					bh.setProductCategory(value);
					attribute = Utility.PRODUCT_CATEGORY;
					break;
				case 9:
					bh.setUrlToNvidia(value);
					attribute = Utility.URL_TO_NVIDIA;
					break;
				case 10:
					bh.setFeaturedOnMainPage(value);
					attribute = Utility.FEATURED_ON_MAIN_PAGE;
					break;
				default:
					break;
				}
				mapAttributeValues.put(attribute, value);
			}
			try {
				String[] productsArr;
				String[] categoriesArr;
				String product;
				String category;
				int productId;

				// if industry category has multiple values
				if (bh.getIndustryCategory().indexOf(";") > 0) {
					productsArr = bh.getIndustryCategory().split(";");
					for (int i = 0; i < productsArr.length; i++) {
						product = productsArr[i].trim();
						productId = productExecution(product, conn);

						// if product category has multiple values
						if (bh.getProductCategory().indexOf(";") > 0) {
							categoriesArr = bh.getProductCategory().split(";");
							for (int j = 0; j < categoriesArr.length; j++) {
								category = categoriesArr[j].trim();

								// remove the previous values having semi-colons
								// replace with new values
								mapAttributeValues.remove(Utility.INDUSTRY_CATEGORY);
								mapAttributeValues.put(Utility.INDUSTRY_CATEGORY, product);
								mapAttributeValues.remove(Utility.PRODUCT_CATEGORY);
								mapAttributeValues.put(Utility.PRODUCT_CATEGORY, category);

								executeQueries(bh, conn, mapAttributeValues, product, category, productId);
							}
						} else {
							category = bh.getProductCategory().trim();
							executeQueries(bh, conn, mapAttributeValues, product, category, productId);
						}
					}
				} else {
					product = bh.getIndustryCategory();
					productId = productExecution(product.trim(), conn);

					// if product category has multiple values
					if (bh.getProductCategory().indexOf(";") > 0) {
						categoriesArr = bh.getProductCategory().split(";");
						for (int j = 0; j < categoriesArr.length; j++) {

							category = categoriesArr[j].trim();

							// remove the previous values having semi-colons
							// replace with new values
							mapAttributeValues.remove(Utility.PRODUCT_CATEGORY);
							mapAttributeValues.put(Utility.PRODUCT_CATEGORY, category);

							executeQueries(bh, conn, mapAttributeValues, product, category, productId);
						}
					} else {
						category = bh.getProductCategory().trim();
						executeQueries(bh, conn, mapAttributeValues, product, category, productId);
					}
				}

			} catch (Exception e) {
				logger.error(com.nvidia.server.catalog.Utility.getExceptionString(e));
			}
		}

		// closing connection
		DBConnection.closeConnection(conn);
	}

	private void executeQueries(BeanHelper bh, Connection conn, Map<String, String> mapAttributeValues, String product,
			String category, int productId) throws SQLException {
		int categoryId;
		int resourceId;
		String md5Sum;
		categoryId = categoryExecution(category, conn);

		// here we are using salt as "1". Here we have to use the resource type
		// id from the resource_type table

		md5Sum = Utility.getMD5Sum(bh.getName(), "1");
		resourceId = resourcesExecution(md5Sum, conn);

		productCategoryExecution(productId, categoryId, resourceId, conn);
		resourceAttributeValueExecution(resourceId, mapAttributeValues, conn);
	}

	/**
	 * Used for execution of queries for table <b>PRODUCT</b>
	 * 
	 * @param product
	 *            name of the product
	 * @param conn
	 *            connection to use
	 * @return record id
	 * @throws SQLException
	 */
	private static int productExecution(String product, Connection conn) throws SQLException {
		PreparedStatement pstmt;

		pstmt = conn.prepareStatement("select p.id from product p where name=?");
		pstmt.setString(1, product);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {

			pstmt = conn.prepareStatement("insert into product (name,created_date) values(?,?)");
			pstmt.setString(1, product);
			pstmt.setTimestamp(2, Timestamp.from(Instant.now()));
			int i = pstmt.executeUpdate();

			if (i > 0) {
				pstmt = conn.prepareStatement("select max(p.id) from product p");
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}

		return 0;
	}

	/**
	 * Used for execution of queries for table <b>CATEGORY</b>
	 * 
	 * @param category
	 *            name of the category
	 * @param conn
	 *            connection to use
	 * @return record id
	 * @throws SQLException
	 */
	private static int categoryExecution(String category, Connection conn) throws SQLException {
		PreparedStatement pstmt;

		pstmt = conn.prepareStatement("select c.id from category c where name=?");
		pstmt.setString(1, category);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {

			pstmt = conn.prepareStatement("insert into category (name,created_date) values(?,?)");
			pstmt.setString(1, category);
			pstmt.setTimestamp(2, Timestamp.from(Instant.now()));
			int i = pstmt.executeUpdate();

			if (i > 0) {
				pstmt = conn.prepareStatement("select max(c.id) from category c");
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}

		return 0;
	}

	/**
	 * Used for execution of queries for table <b>PRODUCT CATEGORY</b>
	 * 
	 * @param productId
	 *            id of the product
	 * @param categoryId
	 *            id of the category
	 * @param conn
	 *            connection to use
	 * @return record id
	 * @throws SQLException
	 */
	private static int productCategoryExecution(int productId, int categoryId, int resourceId, Connection conn)
			throws SQLException {
		PreparedStatement pstmt;

		pstmt = conn.prepareStatement(
				"select pc.id from product_category_resources pc where pc.product_id=? and pc.category_id=? and pc.resource_id=?");
		pstmt.setInt(1, productId);
		pstmt.setInt(2, categoryId);
		pstmt.setInt(3, resourceId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {

			pstmt = conn.prepareStatement(
					"insert into product_category_resources (product_id,category_id,resource_id) values(?,?,?)");
			pstmt.setInt(1, productId);
			pstmt.setInt(2, categoryId);
			pstmt.setInt(3, resourceId);
			int i = pstmt.executeUpdate();

			if (i > 0) {
				pstmt = conn.prepareStatement("select max(pc.id) from product_category_resources pc");
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	/**
	 * Used for execution of queries for table <b>RESOURCES</b>
	 * 
	 * @param md5
	 *            MD5 Sum of the values. Here combination of name, industry category
	 *            and product category is taken as data and resource type id as
	 *            salt.
	 * @param conn
	 *            connection to use
	 * @return record id
	 * @throws SQLException
	 * 
	 * @see Utility#getMD5Sum
	 */
	private static int resourcesExecution(String md5, Connection conn) throws SQLException {
		PreparedStatement pstmt;

		pstmt = conn.prepareStatement("select r.id from resources r where r.unique_id=?");
		pstmt.setString(1, md5);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			pstmt = conn.prepareStatement(
					"insert into resources (unique_id,resource_type_id,country_locale_id,created_date,updated_date) values(?,?,?,?,?)");
			pstmt.setString(1, md5);
			pstmt.setInt(2, 1);
			pstmt.setInt(3, 1);
			pstmt.setTimestamp(4, Timestamp.from(Instant.now()));
			pstmt.setTimestamp(5, Timestamp.from(Instant.now()));
			int i = pstmt.executeUpdate();

			if (i > 0) {
				pstmt = conn.prepareStatement("select max(r.id) from resources r");
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

	/**
	 * Used for execution of queries for table <b>RESOURCE ATTRIBUTE VALUE</b>
	 * 
	 * @param resourceId
	 *            id of the resource. It comes from table <b>RESOURCE</b>
	 * @param mapAttributeValues
	 *            map of attribute and related values that are to be inserted
	 * @param conn
	 *            connection to use
	 * @throws SQLException
	 */
	private static void resourceAttributeValueExecution(int resourceId, Map<String, String> mapAttributeValues,
			Connection conn) throws SQLException {
		PreparedStatement pstmt;

		pstmt = conn.prepareStatement("select rav.id from resource_attribute_value rav where rav.resource_id=?");
		pstmt.setInt(1, resourceId);
		ResultSet rs = pstmt.executeQuery();
		if (!rs.next()) {
			mapAttributeValues.forEach((k, v) -> {
				try {
					PreparedStatement pstmtInner;

					pstmtInner = conn.prepareStatement(
							"insert into resource_attribute_value (resource_id,resource_attribute,resource_attribute_value) values(?,?,?)");
					pstmtInner.setInt(1, resourceId);
					pstmtInner.setString(2, k);
					pstmtInner.setString(3, v);
					pstmtInner.executeUpdate();

				} catch (Exception e) {
					logger.error(com.nvidia.server.catalog.Utility.getExceptionString(e));
				}
			});
		}
	}

	/**
	 * Used to fetch the cell value
	 * 
	 * @param cell
	 *            object of XSSFCell class
	 * @return cell value
	 */
	@SuppressWarnings("deprecation")
	private static String getCellValue(XSSFCell cell) {
		if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
			return "NA";
		}
		return "NA";
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure(com.nvidia.server.catalog.Utility.getProperties().getProperty("log4j.path"));

		if (Optional.ofNullable(args).isPresent() && args.length > 0) {
			if (args[0].equalsIgnoreCase("gpucatalog")) {
				ExcelUtility excelUtility = new ExcelUtility();
				excelUtility.readXLSXFile(
						com.nvidia.server.catalog.Utility.getProperties().getProperty("gpu.catalog.excel.path"));
			} else if (args[0].equalsIgnoreCase("servercatalog")) {

				logger.info("Utility Started at : {}", new Date());
				PartNumberService.postPartNumber();
				logger.info("Utility Finished at  : {}", new Date());
			}

		} else {
			logger.warn(com.nvidia.server.catalog.Utility.getProperties().getProperty("utility.usage.message"));
		}

	}
}
