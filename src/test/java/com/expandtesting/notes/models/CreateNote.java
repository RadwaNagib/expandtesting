package com.expandtesting.notes.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public  class CreateNote {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;
}
