package com.example.fadlou_restaurant;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ItemController {

    private String name;
    private double price;
    private int qty;

    private boolean dashbord;
    private Home home;
    private DashboardController dashboardController;

    private TableMenuController tableMenuController;
    private double orgPrice;
    @FXML
    private JFXButton delete;

    @FXML
    private HBox hbox;

    /* @FXML
     private JFXCheckBox xl;*/
    @FXML
    private Text itemQty;
    @FXML
    private Label itemName;
    @FXML
    private Text itemPrice;
    private int index;


    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setTableMenuController(TableMenuController tableMenuController) {
        this.tableMenuController = tableMenuController;
    }

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

   /* public void check() {

        xl.setOnAction(Event -> {
            if (xl.isSelected()) {

                setPrice(this.orgPrice);
                if(dashbord) {
                    this.home.getSelectedItems().get(this.index).getElement().setXlPrice1(this.price);
                }else                     this.home.getSelectedItems().get(this.index).getElement().setXlPrice2(this.price);

            } else {
                this.price = this.orgPrice;
                this.itemPrice.setText(String.valueOf(this.price));
                if (dashbord) {
                    this.home.getSelectedItems().get(this.index).getElement().setXlPrice1(0);
                }else                     this.home.getSelectedItems().get(this.index).getElement().setXlPrice2(0);

            }
            this.dashboardController.sumItems();
        });

    }
    */


    private void setPrice(double price) {
        switch ((int) price) {
            case 400:
                this.price = 1200;
                break;
            case 500:
                this.price = 1500;
                break;
            case 600:
                this.price = 1800;
                break;
            case 700:
                this.price = 2100;
                break;
            case 850:
                this.price = 2550;
                break;
        }
        this.itemPrice.setText(String.valueOf(this.price));
    }

    @FXML
    void minusQty(ActionEvent event) {

        if (dashbord) {
            if (qty - 1 >= 0) {
                this.qty--;

            } else this.qty = 0;
            this.itemQty.setText(Integer.toString(this.qty));
            for (int i = 0; i < home.getSelectedItems().size(); i++) {
                if (home.getSelectedItems().get(i).getElement().getName().equals(name)) {
                    home.getSelectedItems().get(i).setQuanity(qty);
                    break;
                }
            }
            this.dashboardController.sumItems();
        } else {
            if (qty - 1 >= 0) {
                this.qty--;

            } else this.qty = 0;
            this.itemQty.setText(Integer.toString(this.qty));
            for (int i = 0; i < home.getTables().get(index).getItems().size(); i++) {
                if (home.getTables().get(index).getItems().get(i).getElement().getName().equals(name)) {
                    home.getTables().get(index).getItems().get(i).setQuanity(qty);
                    break;
                }
            }
            this.tableMenuController.sumItems();
        }
    }

    @FXML
    void plusQty(ActionEvent event) {
        if (dashbord) {
            this.qty++;
            this.itemQty.setText(Integer.toString(this.qty));

            for (int i = 0; i < home.getSelectedItems().size(); i++) {
                if (home.getSelectedItems().get(i).getElement().getName().equals(name)) {
                    home.getSelectedItems().get(i).setQuanity(qty);
                    break;
                }
            }
            this.dashboardController.sumItems();
        } else {
            this.qty++;
            this.itemQty.setText(Integer.toString(this.qty));

            for (int i = 0; i < home.getTables().get(index).getItems().size(); i++) {

                if (home.getTables().get(index).getItems().get(i).getElement().getName().equals(name)) {
                    home.getTables().get(index).getItems().get(i).setQuanity(qty);
                    break;
                }
            }
            this.tableMenuController.sumItems();
        }
    }

    @FXML
    void deleteItem(ActionEvent event) {
        if (dashbord) {
            delete1();
        } else delete2();
    }

    public void delete1() {
        for (int i = 0; i < home.getSelectedItems().size(); i++) {
            if (home.getSelectedItems().get(i).getElement().getName().equals(name)) {
                home.getSelectedItems().remove(i);
                break;
            }
        }
        if (dashboardController != null) {
            dashboardController.getItemLayout().getChildren().clear();
            dashboardController.afficher(home.getSelectedItems()); // Update the item list in the DashboardController
        }
        this.dashboardController.sumItems();
    }

    public void delete2() {
        for (int i = 0; i < home.getTables().get(index).getItems().size(); i++) {
            if (home.getTables().get(index).getItems().get(i).getElement().getName().equals(name)) {
                home.getTables().get(index).getItems().remove(i);
                break;
            }
        }
        if (tableMenuController != null) {
            tableMenuController.getList().getChildren().clear();
            tableMenuController.afficher(home.getTables().get(index).getItems()); // Update the item list in the DashboardController
        }
        this.tableMenuController.sumItems();
    }

    public HBox getHbox() {
        return hbox;
    }

    public JFXButton getDelete() {
        return delete;
    }


}

