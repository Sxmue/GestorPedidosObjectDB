package com.cesur.gestorpedidos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static Stage myStage;
    @Override
    public void start(Stage stage) throws IOException {
        //Esta linea es necesaria para poder cambiar entre ventanas!!!
        myStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Gestor de Pedidos");
        stage.setScene(scene);
        Image icon = new Image("img/carro.png");
        stage.getIcons().add(icon);
        stage.show();
        Font.loadFont(getClass().getResourceAsStream("font/Montserrat/M.ttf"), 14);
    }

    public static void loadFXML(String fxml){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));

            Scene scene = null;
            scene = new Scene(fxmlLoader.load(), 700, 500);
            myStage.setScene(scene);

        } catch (IOException e) {
            System.out.println("Error al cargar el fxml");
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        launch();
    }
}
