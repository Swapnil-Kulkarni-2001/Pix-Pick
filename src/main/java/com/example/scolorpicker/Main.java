package com.example.scolorpicker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("Main.fxml"));
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("Style.css");
        stage.setTitle("ScolorPicker");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon.png"));
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        MainController.anotherThread.interrupt();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}
