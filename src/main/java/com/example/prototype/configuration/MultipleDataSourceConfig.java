package com.example.prototype.configuration;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class MultipleDataSourceConfig {
	
	public static final String Prototype_DATASOURCE = "PrototypeDS";
	
    @Bean(Prototype_DATASOURCE)
    @Primary
    public DataSource prototypeDataSource() throws URISyntaxException {
    	
    	
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
		
    	
    	// postgres://fjiqclxzelluzi:e9d8824214cca0cb2e194fe101e8b4948e1933bc51b31d41267a5f2d57525ea0@ec2-52-20-248-222.compute-1.amazonaws.com:5432/d3ffmr12lkpnkq
    	
    	/*String username = "fjiqclxzelluzi";
    	String password = "e9d8824214cca0cb2e194fe101e8b4948e1933bc51b31d41267a5f2d57525ea0";
		String dbUrl = "jdbc:postgresql://ec2-52-20-248-222.compute-1.amazonaws.com:5432/d3ffmr12lkpnkq";*/
		
		/*DataSource dataSource = DataSourceBuilder
				.create()
				.url(dbUrl)
				.username(username)
				.password(password)
				.driverClassName("org.postgresql.Driver")
				.build();
    	*/
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    	return dataSource;
    }

}
