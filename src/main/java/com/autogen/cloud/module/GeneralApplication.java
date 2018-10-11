package com.autogen.cloud.module;

import com.autogen.cloud.module.service.tomodel.GeneralService;
import com.autogen.cloud.module.service.toservice.ResourceServiceGenerator;
import com.autogen.cloud.module.service.totable.EntityClassScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.autogen.cloud.module.service.mapper"})
public class GeneralApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralApplication.class, args);
    }


    @Value("${general.model.switch}")
    private String genenralModelSwitch;
    @Value("${general.table.switch}")
    private String generalTableSwitch;
    @Value("${general.table.package}")
    private String entityPackagePath;

    @Autowired
    private GeneralService generalService;
    @Autowired
    private EntityClassScan classScan;

    @Bean
    public CommandLineRunner generalCodeCommand() {
        return (args) -> {
            if (null != genenralModelSwitch && genenralModelSwitch.equals("on")) {
                generalService.generalCode();
                ResourceServiceGenerator.gen("");
            }

            if (null != generalTableSwitch && generalTableSwitch.equals("on")) {
                classScan.setPackagePath(entityPackagePath);
                classScan.scanAndGeneral();
            }
        };

    }
}
