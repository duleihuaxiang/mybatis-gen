//package com.sudao.cloud.module.config;
//
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by Spector on 2017/6/6.
// */
//@Configuration
//@AutoConfigureAfter(MybatisConfiguration.class)
//public class MapperScanConfiguration {
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer(){
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage("com.autogen.cloud.module.mapper");
//        return mapperScannerConfigurer;
//    }
//}
