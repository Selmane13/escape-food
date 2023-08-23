package com.example.fadlou_restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;

public class FadlouRestaurant extends Application {

    public static void main(String[] args) {
        launch();
    }

    public Home initialiseHome() {


        Home home = null;

        ObjectInputStream in;
        try {
            in = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    "restaurant.dat")));
            try {


                home = ((Home) in.readObject());


            } catch (ClassNotFoundException e) {
                System.out.println(1);
                e.printStackTrace();
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(2);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(3);
            e.printStackTrace();
        }

        if (home == null) {
            return home = new Home();
        } else return home;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FadlouRestaurant.class.getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        RegisterController Controller = fxmlLoader.getController();
        Controller.setHome(initialiseHome());

        stage.setTitle("Escape Food");
        stage.setScene(scene);
        Image image = new Image("file:/C:/Users/pc/Desktop/fadlou/Fadlou_restaurant/src/main/resources/com/example/fadlou_restaurant/pizza.png");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.getIcons().add(image);


        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.show();
    }
}