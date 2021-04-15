package com.example.sweater.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ApiModel(value = "class company", description = "Details about the company.")
public class Company {

    @Id
    @ApiModelProperty(value = "The unique id of the company")
    private Long id;
    @ApiModelProperty(value = "Title of company.", example = "Victory")
    private String title;

    @ApiModelProperty(value = "The year the company was founded.", example = "2021")
    private int founded;

}
