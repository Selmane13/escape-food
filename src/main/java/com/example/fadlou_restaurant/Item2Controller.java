package com.example.fadlou_restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Item2Controller {
    private String name;
    private double price;
    private int qty;

    private boolean dashbord;
    private Home home;
    private DashboardController dashboardController;

    private TableMenuController tableMenuController;
    private double orgPrice;


    @FXML
    private HBox hbox;


    @FXML
    private Text itemQty;
    @FXML
    private Label itemName;
    @FXML
    private Text itemPrice;
    private int index;


    public void setInfo(Home home, String name, double price, int qty, int index, boolean bool) {
        this.index = index;
        this.dashbord = bool;
        this.home = home;
        this.name = name;
        this.price = price;
        this.orgPrice = price;
        this.qty = qty;
        this.itemName.setText(name);
        this.itemPrice.setText(Double.toString(price));
        this.itemQty.setText(Integer.toString(qty));
    }


    public HBox getHbox() {
        return hbox;
    }


}
