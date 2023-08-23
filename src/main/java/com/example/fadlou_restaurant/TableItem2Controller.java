package com.example.fadlou_restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TableItem2Controller {

    private Home home;
    private int index;
    @FXML
    private Label num;

    public void setInfo(Home home, int index) {
        this.home = home;
        this.index = index;
        this.num.setText(String.valueOf(this.home.getTables().get(index).getId()));
    }
}
