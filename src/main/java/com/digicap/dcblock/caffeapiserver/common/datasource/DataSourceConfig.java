package com.digicap.dcblock.caffeapiserver.common.datasource;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource meetingRoomDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(meetingRoomDataSource);
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));
        return (SqlSessionFactory) sqlSessionFactory.getObject();
    }
}
