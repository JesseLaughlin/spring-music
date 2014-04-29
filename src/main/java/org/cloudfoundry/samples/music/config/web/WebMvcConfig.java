package org.cloudfoundry.samples.music.config.web;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.cloudfoundry.samples.music.web.controllers.AlbumController;
import org.cloudfoundry.samples.music.web.helper.SongsService;

@Configuration
@EnableWebMvc
@EnableCaching
@ComponentScan(basePackageClasses = { AlbumController.class })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	public static final String SONG_CACHE_NAME = "songcache";

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/");
		internalResourceViewResolver.setSuffix(".jsp");
		return internalResourceViewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations(
				"/assets/");
		registry.addResourceHandler("/webjars/**").addResourceLocations(
				"/webjars/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public SongsService getSongsService() {
		return new SongsService();
	}

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager manager = new SimpleCacheManager();
		manager.setCaches(Arrays
				.asList(new ConcurrentMapCache(SONG_CACHE_NAME)));
		return manager;
	}
}
