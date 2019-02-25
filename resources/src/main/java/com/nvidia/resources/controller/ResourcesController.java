package com.nvidia.resources.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nvidia.resources.service.ResourcesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping({ "/resources" })
@Api(tags = { "Resources Services" })
public class ResourcesController {
	@Autowired
	private ResourcesService resourcesService;

	@RequestMapping(headers = { "Accept=application/json" }, method=RequestMethod.GET)
	@ApiOperation("This service returns the list of all resources.")
	@Cacheable(value="resources")
	public ResponseEntity<List<Map<String, String>>> findAllByResourceType(
			@RequestParam(value = "resource_type", required = true) Long resourceType,
			@RequestParam(value = "country_locale_id", required = true) Long countryLocaleId) {
		return new ResponseEntity<>(this.resourcesService.findAllResources(resourceType, countryLocaleId),
				HttpStatus.OK);
	}

	@RequestMapping(value = { "/search" }, headers = { "Accept=application/json" }, method=RequestMethod.GET)
	@ApiOperation("This service returns the list of searched resources.")
	public ResponseEntity<List<Map<String, String>>> search(
			@RequestParam(value = "resource_type", required = true) Long resourceType,
			@RequestParam(value = "country_locale_id", required = true) Long countryLocaleId,
			@RequestParam(required = true) String search,
			@RequestParam(value = "resource_ids", required = true) List<Long> resourceIds) {
		return new ResponseEntity<>(
				this.resourcesService.searchResources(resourceIds, resourceType, countryLocaleId, search),
				HttpStatus.OK);
	}
}
