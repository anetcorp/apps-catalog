package com.nvidia.resources.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This is servlet initializer class replace for web.xml file.
 * 
 * @author Satnam Singh
 * @since 1.0
 */
@Configuration	
public class ServletContainerInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addFilter("characterEncodingFilter", characterEncodingFilter()).addMappingForUrlPatterns(null,
				true, "/*");
		servletContext.addFilter("hiddenHttpMethodFilter", hiddenHttpMethodFilter()).addMappingForUrlPatterns(null,
				true, "/*");
		super.onStartup(servletContext);
	}

	/**
	 * Common configuration.
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
				// ApplicationContext.class
		};
	}

	/**
	 * SpringMVC configuration.
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringMVCApplicationContext.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected String getServletName() {
		return "springmvc";
	}

	@Override
	protected boolean isAsyncSupported() {
		return true;
	}

	private Filter characterEncodingFilter() {
		final CharacterEncodingFilter cef = new CharacterEncodingFilter();
		cef.setEncoding("UTF-8");
		return cef;
	}

	/**
	 * 
	 * @return
	 */
	private Filter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

}
