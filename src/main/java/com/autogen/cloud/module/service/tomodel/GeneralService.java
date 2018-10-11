package com.autogen.cloud.module.service.tomodel;

import com.mysql.jdbc.Driver;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spector on 2017/6/7.
 */
@Service
public class GeneralService {

    private static final Logger logger = LoggerFactory.getLogger(GeneralService.class);

    public void generalCode(){
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;

        File configFile = null;

        try {
            configFile = ResourceUtils.getFile( "classpath:mybatis.xml" );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ConfigurationParser cp = new ConfigurationParser(warnings);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        Configuration config = null;
        try {
            String driverPath = getDriverJarPath();

            config = cp.parseConfiguration(configFile);
            config.addClasspathEntry( driverPath );

            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);


            myBatisGenerator.generate(null);

            logger.info("table生成model成功!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getDriverJarPath() {
        String driverPath = Driver.class.getResource( "" ).getPath();
        driverPath = driverPath.substring( driverPath.indexOf( "file:" )+5, driverPath.indexOf( "!" ) );
        return driverPath;
    }
}
