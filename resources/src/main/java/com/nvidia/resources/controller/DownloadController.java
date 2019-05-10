package com.nvidia.resources.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nvidia.resources.service.ResourcesService;
import com.nvidia.resources.util.POIUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/download")
@Api(tags = { "Download Service" })
public class DownloadController {

	@Autowired
	private POIUtils pOIUtils;

	@Autowired
	private ResourcesService resourcesService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

	@RequestMapping(method = { RequestMethod.GET }, value = { "/excel" })
//	@ApiOperation("This service download the excel sheet")
	public void downloadExcel(HttpServletResponse response) {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		try {
			String[] headers = new String[] { "Name", "GPU Catalog Category", "Company Name", "GPU Scaling",
					"Product Description", "Supported Features", "URL to High Value Developer Page",
					"Industry Category", "Product Category"};
			response.getOutputStream()
					.write(this.pOIUtils.exportExcel(headers, (Object[][]) this.getData()).toByteArray());
			response.flushBuffer();
		} catch (IOException e) {
			LOGGER.error("", e);
		}
	}

	private String[][] getData() {
		List<Map<String, String>> allResources = this.resourcesService.findAllResources(Long.valueOf(1),
				Long.valueOf(1));
		String[][] data = new String[allResources.size()][9];
		int[] count = new int[] { 0 };
		allResources.stream().forEach(resourceMap -> {
			data[count[0]][0] = resourceMap.get("name");
			data[count[0]][1] = resourceMap.get("gpu_category_catalog");
			data[count[0]][2] = resourceMap.get("company_name");
			data[count[0]][3] = resourceMap.get("gpu_scaling");
			data[count[0]][4] = resourceMap.get("product_description");
			data[count[0]][5] = resourceMap.get("supported_features");
			data[count[0]][6] = resourceMap.get("url_to_high_value_developer_page");
			data[count[0]][7] = resourceMap.get("industry_category");
			data[count[0]][8] = resourceMap.get("product_category");
			
			int[] arrn = count;
			arrn[0] = arrn[0] + 1;
		});
		return data;
	}
}
