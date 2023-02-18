package com.tulip.OnlineuUpdate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.OnlineUpdate.Entity.FileStoragePojo;

import javafx.application.Application;

@SpringBootApplication
@EnableAsync
@ComponentScan({"com.OnlineUpdate.Controller","com.OnlineUpdate.service"})
@EntityScan("com.OnlineUpdate.Entity")
@EnableJpaRepositories("com.OnlineUpdate.Dao")
@EnableConfigurationProperties({
    FileStoragePojo.class
})
public class OnlineuUpdateApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineuUpdateApplication.class, args);
	}

}
