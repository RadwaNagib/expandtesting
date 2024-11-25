package com.expandtesting.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Login {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

}
