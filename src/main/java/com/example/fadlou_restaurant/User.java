package com.example.fadlou_restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private final String name = "escapeFood";
    private final String password = "password";
    private final List<List<SelectedItem>> history;

    public User() {
        this.history = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
