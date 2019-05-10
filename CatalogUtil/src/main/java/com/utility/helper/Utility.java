package com.utility.helper;

import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;

/**
 * Utility class
 * 
 * @author VINOD KUMAR KASHYAP
 * @since 1.0
 *
 */
public class Utility {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * The value of constant is {@value}
	 */
	public static final String NAME = "name";

	/**
	 * The value of constant is {@value}
	 */
	public static final String GPU_CATALOG_CATEGORY = "gpu_category_catalog";

	/**
	 * The value of constant is {@value}
	 */
	public static final String COMPANY_NAME = "company_name";

	/**
	 * The value of constant is {@value}
	 */
	public static final String GPU_SCALING = "gpu_scaling";

	/**
	 * The value of constant is {@value}
	 */
	public static final String PRODUCT_DESCRIPTION = "product_description";

	/**
	 * The value of constant is {@value}
	 */
	public static final String SUPPORTED_FEATURES = "supported_features";

	/**
	 * The value of constant is {@value}
	 */
	public static final String URL_TO_HIGH = "url_to_high_value_developer_page";

	/**
	 * The value of constant is {@value}
	 */
	public static final String INDUSTRY_CATEGORY = "industry_category";

	/**
	 * The value of constant is {@value}
	 */
	public static final String PRODUCT_CATEGORY = "product_category";

	/**
	 * The value of constant is {@value}
	 */
	public static final String URL_TO_NVIDIA = "url_to_nvidia_page";

	/**
	 * The value of constant is {@value}
	 */
	public static final String FEATURED_ON_MAIN_PAGE = "featured_on_main_page";

	/**
	 * The value of constant is {@value}
	 */
	public static final String DATE_FORMAT = "MMM dd,yyyy HH:mm";

	private Utility() {
	}

	/**
	 * Generates the MD5 Sum
	 * 
	 * @param data
	 *            string object which is a combination of <i>name, industry
	 *            category </i>and <i>product category</i>.
	 * @param salt
	 *            salt to be used for MD5
	 * @return MD5 sum as string
	 */
	public static String getMD5Sum(String data, String salt) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			StringBuilder hash = new StringBuilder(data).append(salt);
			md.update(hash.toString().getBytes());
			byte[] hexBytes = new Hex().encode(md.digest());
			String md5 = new String(hexBytes, "UTF-8");
			return md5;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return "";
	}
}
