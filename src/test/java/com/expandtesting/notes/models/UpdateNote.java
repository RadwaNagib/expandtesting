package com.expandtesting.notes.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateNote {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("completed")
    private boolean completed;

    @JsonProperty("category")
    private String category;
}
