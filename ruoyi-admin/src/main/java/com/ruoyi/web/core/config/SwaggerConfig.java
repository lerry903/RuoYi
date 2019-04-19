package com.ruoyi.web.core.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.config.Global;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2的接口配置
 *
 * @author ruoyi
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 详细定制
                .apiInfo(apiInfo())
                .select()
                // 指定当前包路径
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描所有
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("标题：若依管理系统_接口文档")
                .description("更多内容请关注：https://github.com/lerry903/RuoYi")
                .contact(new Contact(Global.getName(), "https://github.com/lerry903/RuoYi", "lerry903@163.com"))
                .version("版本号:" + Global.getVersion())
                .build();
    }
}
