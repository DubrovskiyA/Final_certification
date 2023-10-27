package com.example.MySpringTests.api;

import com.example.MySpringTests.Model.UserInfo;

import java.io.IOException;

public interface X_clients_auth {
    UserInfo auth() throws IOException;
}
