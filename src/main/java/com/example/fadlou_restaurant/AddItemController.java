package com.example.fadlou_restaurant;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AddItemController {

    private Home home;

    @FXML
    private MFXTextField name;

    @FXML
    private MFXTextField prixAchat;

    @FXML
    private MFXTextField prixVente;

    @FXML
    private ChoiceBox<String> typeChoice;

    @FXML
    void addItem(ActionEvent event) {
        String itemName = name.getText();
        Type itemType = returnType(typeChoice.getValue());
        if (!prixAchat.getText().isEmpty() && !prixVente.getText().isEmpty()) {
            double itemPrice = Double.parseDouble(prixAchat.getText());
            double itemPrice2 = Double.parseDouble(prixVente.getText());
            if (!itemName.isEmpty() && itemPrice > 0) {
                addElem(new Element(itemName, itemType, itemPrice, itemPrice2));
            }
        }
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));


            loader.setControllerFactory(param -> {
                DashboardController controller = new DashboardController();
                controller.setHome(home);
                return controller;
            });

            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {

        }
    }

    public void addElem(Element element) {
        if (!home.getMenu().getMenu().containsKey(element.getName())) {
            home.getMenu().getMenu().put(element.getName(), element);
        }
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Type returnType(String typ) {
        Type type = Type.SANDWICH;
        switch (typ) {
            case "PIZZA":
                type = Type.PIZZA;
                break;
            case "SANDWICH":
                type = Type.SANDWICH;
                break;
            case "BOISSON":
                type = Type.BOISSON;
                break;
            case "DESSERT":
                type = Type.DESSERT;
                break;
            case "TACOS":
                type = Type.TACOS;
                break;
            case "HAMBURGER":
                type = Type.HAMBURGER;
                break;
            case "BOXEPOULET":
                type = Type.BOXEPOULET;
                break;
            case "SUPPLEMENT":
                type = Type.SUPPLEMENT;
                break;
        }
        return type;
    }
}
