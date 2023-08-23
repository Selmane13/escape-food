package com.example.fadlou_restaurant;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditMenuController implements Initializable {

    @FXML
    private VBox itemLayout;
    private Home home;
    @FXML
    private MFXTextField name = new MFXTextField();
    @FXML
    private Text nameTxt = new Text();
    @FXML
    private Text priceTxt = new Text();
    @FXML
    private Text priceTxt2 = new Text();
    @FXML
    private JFXButton editBtn = new JFXButton();
    @FXML
    private MFXTextField prixAchat;

    @FXML
    private MFXTextField prixVente;
    private String exName;
    private double exPrice;
    private double exPrice2;

    public VBox getItemLayout() {
        return itemLayout;
    }

    public void setInfo(String name, double price, double price2) {
        this.name.setText(name);
        this.prixAchat.setText(String.valueOf(price));
        this.prixVente.setText(String.valueOf(price2));
    }


    @FXML
    void modifier(ActionEvent event) {
        if (!this.name.getText().equals(this.exName)) {
            Element element = home.getMenu().getMenu().get(this.exName);
            System.out.println(1);
            home.getMenu().getMenu().putIfAbsent(this.name.getText(), new Element(this.name.getText(), element.getType(), element.getPrixAchat(), element.getPrixVente()));
            home.getMenu().getMenu().remove(this.exName);
        }
        System.out.println(0);
        if (Double.parseDouble(this.prixAchat.getText()) != this.exPrice) {
            Element element = home.getMenu().getMenu().get(this.name.getText());
            System.out.println(2);
            home.getMenu().getMenu().replace(this.name.getText(), element, new Element(this.name.getText(), element.getType(), Double.parseDouble(this.prixAchat.getText()), Double.parseDouble(this.prixVente.getText())));
        }
        if (Double.parseDouble(this.prixVente.getText()) != this.exPrice2) {
            Element element1 = home.getMenu().getMenu().get(this.name.getText());
            System.out.println(2);
            home.getMenu().getMenu().replace(this.name.getText(), element1, new Element(this.name.getText(), element1.getType(), Double.parseDouble(this.prixAchat.getText()), Double.parseDouble(this.prixVente.getText())));
        }
        itemLayout.getChildren().clear();
        afficher();


    }

    public void makeVisible() {
        this.name.setVisible(true);
        this.prixAchat.setVisible(true);
        this.prixVente.setVisible(true);
        this.editBtn.setVisible(true);
        this.nameTxt.setText("Nom");
        this.priceTxt.setText("Prix d'Achat");
        this.priceTxt.setText("Prix d'Achat");
        this.priceTxt2.setText("Prix de Vente");
    }

    public void afficher() {
        for (String name : home.getMenu().getMenu().keySet()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("editItem.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                EditItemController editItemController = fxmlLoader.getController();
                editItemController.setEditMenuController(this);
                editItemController.setInfo(name, home.getMenu().getMenu().get(name).getPrixAchat(), home.getMenu().getMenu().get(name).getPrixVente(), home);
                itemLayout.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        this.name.setVisible(false);
        this.prixAchat.setVisible(false);
        this.prixVente.setVisible(false);
        this.editBtn.setVisible(false);
        this.nameTxt.setText("");
        this.priceTxt.setText("");
        this.priceTxt2.setText("");

    }

    public void setHome(Home home) {
        this.home = home;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public void setExPrice(double exPrice) {
        this.exPrice = exPrice;
    }

    public void setExPrice2(double exPrice2) {
        this.exPrice2 = exPrice2;
    }
}
