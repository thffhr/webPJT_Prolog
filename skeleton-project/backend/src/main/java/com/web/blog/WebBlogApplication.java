package com.web.blog;

import com.web.blog.property.FileUploadProperties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
@EnableConfigurationProperties({ FileUploadProperties.class })
public class WebBlogApplication implements CommandLineRunner {

	public static void main(final String[] args) {

		SpringApplication.run(WebBlogApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
		// git ignore3
	}

	@Override
	@Transactional
	public void run(final String... args) throws Exception {

	}

}
