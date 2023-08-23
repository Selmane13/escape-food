package com.example.fadlou_restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TableItem1Controller {

    @FXML
    private Label numero;
    private ChooseTableController chooseTableController;
    private Home home;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setInfo(ChooseTableController controller, int id) {
        this.chooseTableController = controller;
        this.home = home;
        this.index = index;
        this.numero.setText(String.valueOf(id));
    }

}
