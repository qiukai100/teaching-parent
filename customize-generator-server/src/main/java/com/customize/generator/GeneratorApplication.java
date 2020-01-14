package com.customize.generator;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@ComponentScan(basePackages = {
        "com.customize.generator"
})
public class GeneratorApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GeneratorApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
