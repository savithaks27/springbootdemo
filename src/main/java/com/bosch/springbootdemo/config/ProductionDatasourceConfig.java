package com.bosch.springbootdemo.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProductionDatasourceConfig implements DatasourceConfig{
    @Override
    public void setup() {
        System.out.println("Setting up datasource for PRODUCTION environment. ");
    }
}
