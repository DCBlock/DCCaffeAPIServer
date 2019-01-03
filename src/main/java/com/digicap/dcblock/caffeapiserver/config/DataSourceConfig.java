package com.digicap.dcblock.caffeapiserver.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.digicap.dcblock.caffeapiserver")
public class DataSourceConfig {

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
    // mybatis의 xml들의 위치를 Bean에 등록.
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource);
    sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));
    return (SqlSessionFactory) sqlSessionFactory.getObject();
  }
}
