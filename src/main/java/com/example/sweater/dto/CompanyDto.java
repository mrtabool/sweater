package com.example.sweater.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyDto {

    @JsonProperty(value = "id")
    private Long id = null;

    @JsonProperty(value = "title")
    private String title = null;

    @JsonProperty(value = "founded")
    private Integer founded = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFounded() {
        return founded;
    }

    public void setFounded(Integer founded) {
        this.founded = founded;
    }
}
