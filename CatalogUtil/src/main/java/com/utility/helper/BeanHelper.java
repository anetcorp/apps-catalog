package com.utility.helper;

/**
 * Helper Bean for utility
 * 
 * @author VINOD KUMAR KASHYAP
 *
 * @since 1.0
 *
 */
public class BeanHelper {

	private String name;
	private String gpuCatalogCategory;
	private String companyName;
	private String gpuScaling;
	private String description;
	private String features;
	private String urlToHigh;
	private String industryCategory;
	private String productCategory;
	private String urlToNvidia;
	private String featuredOnMainPage;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gpuCatalogCategory
	 */
	public String getGpuCatalogCategory() {
		return gpuCatalogCategory;
	}

	/**
	 * @param gpuCatalogCategory
	 *            the gpuCatalogCategory to set
	 */
	public void setGpuCatalogCategory(String gpuCatalogCategory) {
		this.gpuCatalogCategory = gpuCatalogCategory;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the gpuScaling
	 */
	public String getGpuScaling() {
		return gpuScaling;
	}

	/**
	 * @param gpuScaling
	 *            the gpuScaling to set
	 */
	public void setGpuScaling(String gpuScaling) {
		this.gpuScaling = gpuScaling;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the features
	 */
	public String getFeatures() {
		return features;
	}

	/**
	 * @param features
	 *            the features to set
	 */
	public void setFeatures(String features) {
		this.features = features;
	}

	/**
	 * @return the urlToHigh
	 */
	public String getUrlToHigh() {
		return urlToHigh;
	}

	/**
	 * @param urlToHigh
	 *            the urlToHigh to set
	 */
	public void setUrlToHigh(String urlToHigh) {
		this.urlToHigh = urlToHigh;
	}

	/**
	 * @return the industryCategory
	 */
	public String getIndustryCategory() {
		return industryCategory;
	}

	/**
	 * @param industryCategory
	 *            the industryCategory to set
	 */
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	/**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}

	/**
	 * @param productCategory
	 *            the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	/**
	 * @return the urlToNvidia
	 */
	public String getUrlToNvidia() {
		return urlToNvidia;
	}

	/**
	 * @param urlToNvidia
	 *            the urlToNvidia to set
	 */
	public void setUrlToNvidia(String urlToNvidia) {
		this.urlToNvidia = urlToNvidia;
	}

	/**
	 * @return the featuredOnMainPage
	 */
	public String getFeaturedOnMainPage() {
		return featuredOnMainPage;
	}

	/**
	 * @param featuredOnMainPage
	 *            the featuredOnMainPage to set
	 */
	public void setFeaturedOnMainPage(String featuredOnMainPage) {
		this.featuredOnMainPage = featuredOnMainPage;
	}

	@Override
	public String toString() {
		return "BeanHelper [name=" + name + ", gpuCatalogCategory=" + gpuCatalogCategory + ", companyName="
				+ companyName + ", gpuScaling=" + gpuScaling + ", description=" + description + ", features=" + features
				+ ", urlToHigh=" + urlToHigh + ", industryCategory=" + industryCategory + ", productCategory="
				+ productCategory + ", urlToNvidia=" + urlToNvidia + ", featuredOnMainPage=" + featuredOnMainPage + "]";
	}

}
