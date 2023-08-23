package com.example.fadlou_restaurant;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseTableController implements Initializable {

    private Home home;
    private Table selectedTable;
    private DashboardController dashboardController;
    private int index;

    private Stage motherStage;
    @FXML
    private JFXButton confirmer;

    @FXML
    private Label num;

    @FXML
    private VBox items;

    public void setInfo(Home home, DashboardController dashboardController) {
        this.home = home;
        this.dashboardController = dashboardController;
    }

    public void afficher() {
        Table table = null;
        for (int i = 0; i < this.home.getTables().size(); i++) {
            table = this.home.getTables().get(i);
            if (table.isVide()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("tableItem1.fxml"));
                try {
                    Pane pane = fxmlLoader.load();
                    TableItem1Controller itemController = fxmlLoader.getController();
                    itemController.setInfo(this, table.getId());
                    Table finalTable = table;
                    items.getChildren().add(pane);
                    int finalI = i;
                    pane.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            this.index = finalI;
                            this.selectedTable = finalTable;
                            this.num.setText(String.valueOf(finalTable.getId()));

                            // Apply a fade-in effect
                            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), pane);
                            fadeTransition.setFromValue(0.0);
                            fadeTransition.setToValue(1.0);
                            fadeTransition.play();
                        }
                    });

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        if (items.getChildren().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune table vide");
            alert.setContentText("Veuillez attendre qu'une table soit vide ");
            alert.showAndWait();
            motherStage.close();
        }

    }

    @FXML
    void confirmer(ActionEvent event) {
        if (!this.num.getText().isEmpty()) {
            this.home.getTables().get(index).setVide(false);
            this.home.getTables().get(index).getItems().addAll(this.home.getSelectedItems());
            home.getSelectedItems().removeAll(home.getSelectedItems());
            this.dashboardController.getItemLayout().getChildren().clear();
            this.dashboardController.getTableList().getChildren().clear();
            this.dashboardController.afficherTables();
            this.dashboardController.setTotal(0);
        }
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher();
    }

    public void setMotherStage(Stage motherStage) {
        this.motherStage = motherStage;
    }
}
