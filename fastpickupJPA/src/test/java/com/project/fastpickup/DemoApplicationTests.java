package com.project.fastpickup;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	public void testConnection() {
		try (Connection connection = dataSource.getConnection()){
			log.info("Is Ok Database Connection");
		} catch (Exception e) {
			log.info("Not Ok Database Connection");
		}
	}
}
