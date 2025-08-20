package com.ticketsystem.config;

//import com.ticketsystem.security.AdminOnlyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
//    private final AdminOnlyInterceptor adminOnlyInterceptor;


    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        // Đường dẫn tới thư mục chứa template
        templateResolver.setPrefix("classpath:/templates/");
        // Hậu tố của file template
        templateResolver.setSuffix(".html");
        // Kiểu template
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // Encoding
        templateResolver.setCharacterEncoding("UTF-8");
        // Tắt cache, tương đương spring.thymeleaf.cache=false
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(adminOnlyInterceptor)
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns(
//                        "/admin/assets/**",
//                        "/admin/css/**",
//                        "/admin/js/**",
//                        "/admin/images/**"
//                );
//    }
}