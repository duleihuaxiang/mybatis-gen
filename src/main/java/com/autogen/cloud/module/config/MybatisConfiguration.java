//package com.sudao.cloud.module.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//public class MybatisConfiguration implements TransactionManagementConfigurer {
//
//    @Qualifier("dataSource")
//    @Autowired
//    private DataSource dataSource;
//
//    /**
//     * Return the default transaction manager bean to use for annotation-driven database
//     * transaction management, i.e. when processing {@code @Transactional} methods.
//     * <p>There are two basic approaches to implementing this method:
//     * <h3>1. Implement the method and annotate it with {@code @Bean}</h3>
//     * In this case, the implementing {@code @Configuration} class implements this method,
//     * marks it with {@code @Bean} and configures and returns the transaction manager
//     * directly within the method body:
//     * <pre class="code">
//     * &#064;Bean
//     * &#064;Override
//     * public PlatformTransactionManager annotationDrivenTransactionManager() {
//     * return new DataSourceTransactionManager(dataSource());
//     * }</pre>
//     * <h3>2. Implement the method without {@code @Bean} and delegate to another existing
//     * {@code @Bean} method</h3>
//     * <pre class="code">
//     * &#064;Bean
//     * public PlatformTransactionManager txManager() {
//     * return new DataSourceTransactionManager(dataSource());
//     * }
//     * <p>
//     * &#064;Override
//     * public PlatformTransactionManager annotationDrivenTransactionManager() {
//     * return txManager(); // reference the existing {@code @Bean} method above
//     * }</pre>
//     * If taking approach #2, be sure that <em>only one</em> of the methods is marked
//     * with {@code @Bean}!
//     * <p>In either scenario #1 or #2, it is important that the
//     * {@code PlatformTransactionManager} instance is managed as a Spring bean within the
//     * container as all {@code PlatformTransactionManager} implementations take advantage
//     * of Spring lifecycle callbacks such as {@code InitializingBean} and
//     * {@code BeanFactoryAware}.
//     */
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean("sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactoryBean(){
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//
//        try {
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
