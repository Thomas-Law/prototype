package com.example.prototype.configuration;

import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.example.prototype.mapper"}, sqlSessionFactoryRef = "prototypeSessionFactory")
public class MybatisPrototypeConfig {
	
	private static final String PROTOTYPE_SESSION_FACTORY = "prototypeSessionFactory";
	
	@Autowired
	@Qualifier("PrototypeDS")
	private DataSource prototypeDataSource;

	@Bean(PROTOTYPE_SESSION_FACTORY)
	public SqlSessionFactory prototypeSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("/mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setDataSource(prototypeDataSource);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mybatis/mapper/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSessionTemplate arsSqlSessionTemplate() throws Exception {
		 SqlSessionTemplate template = new SqlSessionTemplate(prototypeSqlSessionFactoryBean());
		 return template;
	}
	
	@Bean("PrototypeTxnMgr")
	public DataSourceTransactionManager prototypeDataSourceTransactionManager() throws URISyntaxException {
		return new DataSourceTransactionManager(prototypeDataSource);
	}
	
}
