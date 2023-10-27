package com.example.MySpringTests.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public class UserInfo {
    private String userToken;

    public String getUserToken() {
        return userToken;
    }
}
