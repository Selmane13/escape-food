package com.example.fadlou_restaurant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DailyHistoryController implements Initializable {
    @FXML
    private VBox historyItem;
    @FXML
    private Label total;

    @FXML
    private Label profit;

    @FXML
    private VBox hystoryItems;
    private Home home;
    private LocalDate date;

    public void afficher() {
        int i = 0;
        for (int j = 0; j < home.getSalesHistory().get(date).size(); j++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("historyItem.fxml"));
            try {
                i++;
                Pane pane = fxmlLoader.load();
                HistoryItemController historyItemController = fxmlLoader.getController();
                historyItemController.setSelctedItems(this.home.getSalesHistory().get(date).get(j).getItems());
                historyItemController.setTotal(home.getSalesHistory().get(date).get(j).getTotal());
                historyItemController.setHome(this.home);
                historyItemController.setHistoryController(this);
                historyItemController.setInfo("Commande " + (i), date, false, j);
                hystoryItems.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public VBox getHistoryItem() {
        return historyItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
    }

    public Label getTotal() {
        return total;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Label getProfit() {
        return profit;
    }
}
