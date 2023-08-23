package com.example.fadlou_restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AllHistoryController implements Initializable {
    @FXML
    private VBox allHistoryItems;


    @FXML
    private Label date;

    @FXML
    private Label profit;

    @FXML
    private Label totalVendu;


    private Home home;
    private LocalDate currDate;


    public void setHome(Home home) {
        this.home = home;
    }

    public void afficher() {
        int i = 0;
        for (LocalDate date : home.getSalesHistory().keySet()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("historyItem.fxml"));
            try {
                i++;
                Pane pane = fxmlLoader.load();
                HistoryItemController historyItemController = fxmlLoader.getController();
                historyItemController.setHome(this.home);
                historyItemController.setAllHistoryController(this);
                historyItemController.setInfo("Jours " + (i), date, true, i);
                allHistoryItems.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
    }

    public Label getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public Label getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit.setText(profit);
    }

    public Label getTotalVendu() {
        return totalVendu;
    }

    public void setTotalVendu(String totalVendu) {
        this.totalVendu.setText(totalVendu);
    }

    @FXML
    void consulterCommandes(ActionEvent event) {
        if (this.date.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun jours selectionné");
            alert.setContentText("SVP selectionnez un jours");
            alert.showAndWait();
        } else {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("dailyHistory.fxml"));

                loader.setControllerFactory(param -> {
                    DailyHistoryController controller = new DailyHistoryController();
                    controller.setHome(home);
                    controller.setDate(this.currDate);
                    return controller;

                });

                Parent root = loader.load();


                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrDate(LocalDate currDate) {
        this.currDate = currDate;
    }
}
