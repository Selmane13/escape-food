package com.example.fadlou_restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class EditItemController {

    @FXML
    private Label name;

    @FXML
    private Text prix;
    private double prixAchat;
    private Home home;

    private EditMenuController editMenuController;

    public void setInfo(String name, double price, double prixVente, Home home) {
        this.name.setText(name);
        this.prix.setText(String.valueOf(prixVente));
        this.prixAchat = price;
        this.home = home;
    }

    @FXML
    void editItem(ActionEvent event) {
        this.editMenuController.makeVisible();
        this.editMenuController.setExName(this.name.getText());
        this.editMenuController.setExPrice(Double.parseDouble(this.prix.getText()));
        this.editMenuController.setExPrice2(this.prixAchat);
        this.editMenuController.setInfo(this.name.getText(), prixAchat, Double.parseDouble(this.prix.getText()));
    }

    @FXML
    void supp() {
        home.getMenu().getMenu().remove(this.name.getText());
        this.editMenuController.getItemLayout().getChildren().clear();
        this.editMenuController.afficher();
    }

    public void setEditMenuController(EditMenuController editMenuController) {
        this.editMenuController = editMenuController;
    }
}
