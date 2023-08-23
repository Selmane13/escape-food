package com.example.fadlou_restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {
    private final int id;
    private boolean vide;
    private final List<SelectedItem> items;

    public Table(int id) {
        this.id = id;
        this.vide = true;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public boolean isVide() {
        return vide;
    }

    public void setVide(boolean bool) {
        this.vide = bool;
    }

    public List<SelectedItem> getItems() {
        return items;
    }
}
