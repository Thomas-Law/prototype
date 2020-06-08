package com.example.prototype.service;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

// import com.zaxxer.hikari.HikariConfig;
// import com.zaxxer.hikari.HikariDataSource;

@Service
public class DataSourceService {
	
	// @Value("${spring.datasource.url}")
	// private String dbUrl;
	
	public String getDbUrl() {
		return System.getenv("DATABASE_URL");
	}
	
	/*
	public DataSource dataSource() throws SQLException {
		if (dbUrl == null || dbUrl.isEmpty()) {
			return new HikariDataSource();
		} else {
			
			 Properties props = new Properties();

			 props.setProperty("dataSourceClassName", "org.postgresql.Driver");
			 /props.setProperty("dataSource.user", "test");
			 props.setProperty("dataSource.password", "test");
			 props.setProperty("dataSource.databaseName", "mydb");
			 props.put("dataSource.logWriter", new PrintWriter(System.out));

			 HikariConfig config = new HikariConfig(props);
			 HikariDataSource ds = new HikariDataSource(config);/
			
			
			HikariConfig config = new HikariConfig(props);
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}
	*/
	
}


