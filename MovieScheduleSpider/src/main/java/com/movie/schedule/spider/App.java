package com.movie.schedule.spider;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.movie.schedule.spider.service.SpiderService;

/**
 * 
 *
 */
@SpringBootApplication
public class App {
	
	@Resource
	private Environment env;
	
	@Autowired
	SpiderService spiderService;

	@PostConstruct
	public void testSpiderService() throws IOException {
		spiderService.processMovies();
	}

	public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
