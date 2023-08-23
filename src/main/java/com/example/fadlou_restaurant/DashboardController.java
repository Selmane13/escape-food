package com.example.fadlou_restaurant;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class DashboardController implements Initializable {
    private List<SelectedItem> slecetedItems;
    private Home home;

    @FXML
    private HBox tableList;
    @FXML
    private MFXScrollPane tableScrollPane;
    @FXML
    private VBox itemLayout;
    @FXML
    private Accordion menu;
    @FXML
    private Text total;

    public HBox getTableList() {
        return tableList;
    }

    public void setTotal(double total) {
        this.total.setText(Double.toString(total));
    }

    public VBox getItemLayout() {
        return itemLayout;
    }

    public void afficher(List<SelectedItem> slecetedItems) {
        for (int i = 0; i < slecetedItems.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setDashboardController(this);
                itemController.setInfo(home, slecetedItems.get(i).getElement().getName(), slecetedItems.get(i).getElement().getPrixVente(), 1, i, true);
                itemLayout.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        sumItems();

    }

    public void afficherTables() {
        for (int i = 0; i < this.home.getTables().size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("tableItem2.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                TableItem2Controller itemController = fxmlLoader.getController();
                itemController.setInfo(home, i);
                if (this.home.getTables().get(i).isVide()) {
                    pane.setStyle("-fx-background-color:  #008000;-fx-border-width: 0px 5px 0px 0px;-fx-border-color: #333333");
                } else {
                    pane.setStyle("-fx-background-color:   #FF0000;-fx-border-width: 0px 5px 0px 0px;-fx-border-color: #333333");
                    int finalI = i;

                    // Apply fade-in effect when hovering over the pane
                    FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), pane);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.7);

                    pane.setOnMouseEntered(event -> {
                        fadeTransition.playFromStart();
                    });

                    pane.setOnMouseExited(event -> {
                        fadeTransition.stop();
                        pane.setOpacity(1.0);
                    });

                    pane.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("tableMenu.fxml"));

                                loader.setControllerFactory(param -> {
                                    TableMenuController controller = new TableMenuController();
                                    controller.setInfo(home, this, finalI);
                                    return controller;
                                });
                                Parent root = loader.load();

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.showAndWait();

                                menu.getPanes().clear();
                                drawMenu();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                tableList.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public TitledPane createTitledPane(String title) {
        MFXScrollPane scrollPane = new MFXScrollPane();
        VBox paneContent = new VBox();
        // paneContent.setStyle("-fx-background-color: #dd571c");
        if (!home.getMenu().getMenu().isEmpty()) {
            for (String name : home.
                    getMenu().getMenu().keySet()) {
                if (home.
                        getMenu().getMenu().get(name).getType().equals(Type.valueOf(title))) {
                    TilePane pane = new TilePane();
                    pane.setStyle("-fx-alignment: center;-fx-pref-height: 30px;-fx-font-size: 15px; -fx-border-width: 0px 0px 2px 0px;-fx-border-color: #000000;-fx-background-color: #f6c03b");
                    pane.getChildren().add(new Text(name));
                    paneContent.getChildren().add(pane);
                    pane.setOnMouseEntered(e -> {
                        pane.setStyle("-fx-alignment: center;-fx-pref-height: 30px;-fx-font-size: 15px; -fx-border-width: 0px 0px 2px 0px;-fx-border-color: #000000;-fx-background-color: #eda316");
                    });
                    pane.setOnMouseExited(e -> {
                        pane.setStyle("-fx-alignment: center;-fx-pref-height: 30px;-fx-font-size: 15px; -fx-border-width: 0px 0px 2px 0px;-fx-border-color: #000000;-fx-background-color: #f6c03b");

                    });
                    pane.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                            handleDoubleClick(pane);
                        }
                    });
                }
            }
        }
        paneContent.setStyle("-fx-pref-width: 410px;-fx-max-width: 410px;-fx-min-width: 410px;-fx-padding: 5px 5px 5px 5px");
        scrollPane.setContent(paneContent);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return new TitledPane(title, scrollPane);
    }


    public void setHome(Home home) {
        this.home = home;
    }

    public void drawMenu() {
        menu.getPanes().clear();
        Set<Type> enumSet = EnumSet.of(Type.PIZZA, Type.SANDWICH, Type.TACOS, Type.HAMBURGER, Type.BOXEPOULET, Type.DESSERT, Type.BOISSON, Type.SUPPLEMENT);
        for (Type title : enumSet) {
            TitledPane pane = createTitledPane(title.toString());
            pane.getStyleClass().add("custom-titled-pane");
            pane.setStyle("-fx-background-color: #f25b0a; -fx-text-fill: white;");
            this.menu.getPanes().add(pane);
        }
    }

    private void handleDoubleClick(TilePane pane) {
        home.selectItem(((Text) pane.getChildren().get(0)).getText());
        itemLayout.getChildren().clear();
        sumItems();
        afficher(home.getSelectedItems());
    }

    public void sumItems() {
        this.slecetedItems = home.getSelectedItems();
        double sum = 0;
        double price;
        int qte;
        for (int i = 0; i < slecetedItems.size(); i++) {
            price = this.slecetedItems.get(i).getElement().getPrixVente();
            qte = this.slecetedItems.get(i).getQuanity();
            sum += price * qte;
        }
        total.setText(String.valueOf(sum));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        total.setText(String.valueOf(0.0));
        drawMenu();
        afficherTables();
    }

    @FXML
    void addMenu(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("addItem.fxml"));

            loader.setControllerFactory(param -> {
                AddItemController controller = new AddItemController();
                controller.setHome(home);
                return controller;

            });

            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();


            menu.getPanes().clear();

            drawMenu();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteAll(ActionEvent event) {
        home.getSelectedItems().removeAll(home.getSelectedItems());
        itemLayout.getChildren().clear();
        sumItems();
    }

    @FXML
    void saveSale(ActionEvent event) {
        if (this.home.getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun élément selectionné");
            alert.setContentText("SVP selectionnez au moins un élément du menu");
            alert.showAndWait();
        } else {
            home.addSale(LocalDate.now(), new HistoryItem(home.getSelectedItems(), LocalDate.now(), Double.parseDouble(total.getText())));
            home.getSelectedItems().removeAll(home.getSelectedItems());
            itemLayout.getChildren().clear();
            sumItems();
        }
    }

    @FXML
    void history() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("allHistory.fxml"));

            loader.setControllerFactory(param -> {
                AllHistoryController controller = new AllHistoryController();
                controller.setHome(home);
                return controller;

            });

            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();


            menu.getPanes().clear();

            drawMenu();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editMenu(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("editMenu.fxml"));

            loader.setControllerFactory(param -> {
                EditMenuController controller = new EditMenuController();
                controller.setHome(home);
                return controller;

            });
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();


            menu.getPanes().clear();

            drawMenu();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void suppTable(ActionEvent event) {
        if (!this.home.getTables().isEmpty()) {
            this.home.getTables().remove(home.getTables().size() - 1);
            tableList.getChildren().clear();
            afficherTables();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Liste des tables vide");
            alert.setContentText("SVP Ajoutez au moins unr table");
            alert.showAndWait();
        }
    }


    @FXML
    void ajouterTable(ActionEvent event) {
        this.home.getTables().add(new Table(home.getTables().size() + 1));
        tableList.getChildren().clear();
        afficherTables();
    }

    @FXML
    void ajouterUneTable(ActionEvent event) {
        if (this.home.getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun élément selectionné");
            alert.setContentText("SVP selectionnez au moins un élément du menu");
            alert.showAndWait();
        } else {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("chooseTable.fxml"));

                loader.setControllerFactory(param -> {
                    ChooseTableController controller = new ChooseTableController();
                    controller.setInfo(home, this);
                    return controller;

                });

                Parent root = loader.load();


                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                // Get reference to the mother stage
                Stage motherStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Pass the reference to the mother stage to the ChooseTableController
                ChooseTableController chooseTableController = loader.getController();
                chooseTableController.setMotherStage(motherStage);

                stage.showAndWait();

                menu.getPanes().clear();
                drawMenu();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void addItem(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("plusItem.fxml"));

            loader.setControllerFactory(param -> {
                PlusItemController controller = new PlusItemController();
                controller.setInfo(home, this,null,-1,true);
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

