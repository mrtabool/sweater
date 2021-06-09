package com.example.sweater;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.modelmapper.ModelMapper;

import java.util.Collections;


@SpringBootApplication
@EnableSwagger2
public class ServingWebContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .build()
                .apiInfo(apiDetails())
                .directModelSubstitute(Pageable.class, SwaggerPageable.class);
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


    @Getter
    private static class SwaggerPageable {

        @ApiParam(value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported.", example = "id,asc")
        @Nullable
        private String sort;

        @ApiParam(value = "Results page you want to retrieve (0..N)", example = "0")
        @Nullable
        private Integer page;

        @ApiParam(value = "Number of records per page", example = "10")
        @Nullable
        private Integer size;
    }


//    @Bean
//    public CommandLineRunner init(CompanyRepo companyRepo) {
//        return args -> {
//            companyRepo.save(new Company(1l, "sazzad", 1995));
//            companyRepo.save(new Company(2l, "Rony", 1993));
//            companyRepo.save(new Company(3l, "Naim", 1996));
//            companyRepo.save(new Company(4l, "Dania", 1995));
//            companyRepo.save(new Company(5l, "Mamun", 1993));
//            companyRepo.save(new Company(6l, "Rimi", 1995));
//            companyRepo.save(new Company(7l, "Habib", 1991));
//            companyRepo.save(new Company(8l, "Shail", 1999));
//            companyRepo.save(new Company(9l, "Pranjol", 1998));
//            companyRepo.save(new Company(10l, "Shohag", 1992));
//            companyRepo.save(new Company(11l, "Ramjan", 1993));
//            companyRepo.save(new Company(12l, "Ashik", 1996));
//            companyRepo.save(new Company(13l, "Kibria", 1995));
//            companyRepo.save(new Company(14l, "Aurik", 1997));
//            companyRepo.save(new Company(15l, "Tanvir", 1998));
//            companyRepo.save(new Company(16l, "Nazmul", 1995));
//            companyRepo.save(new Company(17l, "Mizan", 1996));
//            companyRepo.save(new Company(18l, "Anik", 1998));
//            companyRepo.save(new Company(19l, "Mehedi", 1997));
//            companyRepo.save(new Company(20l, "Shahadat", 1999));
//            companyRepo.save(new Company(21l, "Dipak", 1995));
//            companyRepo.save(new Company(22l, "Mukta", 1997));
//            companyRepo.save(new Company(23l, "Rabaet", 1997));
//            companyRepo.save(new Company(24l, "Lopa", 1995));
//            companyRepo.save(new Company(25l, "Markes", 1997));
//            companyRepo.save(new Company(26l, "Valentino", 1996));
//            companyRepo.save(new Company(27l, "Fotik", 1991));
//            companyRepo.save(new Company(28l, "Lubna", 1992));
//        };
//    }

}
