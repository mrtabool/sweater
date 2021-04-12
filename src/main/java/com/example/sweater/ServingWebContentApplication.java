package com.example.sweater;

import com.example.sweater.domain.Company;
import com.example.sweater.repos.CompanyRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@SpringBootApplication
@EnableSwagger2
public class ServingWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "DB Controller",
                "A simple application for managing databases of machines and companies.",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Kyrylo Chornobryvets GitHub", "https://github.com/mrtabool/sweater", "d@b.com"),
                "API License",
                "https://github.com/mrtabool/sweater",
                Collections.emptyList());
    }


//    @Bean
//    public CommandLineRunner init(CompanyRepo companyRepo) {
//        return args -> {
//            companyRepo.save(new Company(null, "sazzad", 1995));
//            companyRepo.save(new Company(null, "Rony", 1993));
//            companyRepo.save(new Company(null, "Naim", 1996));
//            companyRepo.save(new Company(null, "Dania", 1995));
//            companyRepo.save(new Company(null, "Mamun", 1993));
//            companyRepo.save(new Company(null, "Rimi", 1995));
//            companyRepo.save(new Company(null, "Habib", 1991));
//            companyRepo.save(new Company(null, "Shail", 1999));
//            companyRepo.save(new Company(null, "Pranjol", 1998));
//            companyRepo.save(new Company(null, "Shohag", 1992));
//            companyRepo.save(new Company(null, "Ramjan", 1993));
//            companyRepo.save(new Company(null, "Ashik", 1996));
//            companyRepo.save(new Company(null, "Kibria", 1995));
//            companyRepo.save(new Company(null, "Aurik", 1997));
//            companyRepo.save(new Company(null, "Tanvir", 1998));
//            companyRepo.save(new Company(null, "Nazmul", 1995));
//            companyRepo.save(new Company(null, "Mizan", 1996));
//            companyRepo.save(new Company(null, "Anik", 1998));
//            companyRepo.save(new Company(null, "Mehedi", 1997));
//            companyRepo.save(new Company(null, "Shahadat", 1999));
//            companyRepo.save(new Company(null, "Dipak", 1995));
//            companyRepo.save(new Company(null, "Mukta", 1997));
//            companyRepo.save(new Company(null, "Rabaet", 1997));
//            companyRepo.save(new Company(null, "Lopa", 1995));
//            companyRepo.save(new Company(null, "Markes", 1997));
//            companyRepo.save(new Company(null, "Valentino", 1996));
//            companyRepo.save(new Company(null, "Fotik", 1991));
//            companyRepo.save(new Company(null, "Lubna", 1992));
//        };
//    }

}
