package com.example.scolorpicker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
public class AboutDialogController
{

    public Hyperlink repo_link;

    public static Stage stage;

    public static void display() throws IOException {

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("AboutDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PIX-Pick");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon.png"));
        stage.showAndWait();
    }

    @FXML
    public void repoLinkClicked()
    {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/Swapnil-Kulkarni-2001/Pix-Pick"));
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }
}
