package com.example.scolorpicker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InputDialog
{

    @FXML
    public TextField txt_comment;

    @FXML
    public Button btn_ok;

    @FXML
    public Button btn_cancel;

    public static Stage stage;

    public static void display() throws IOException {

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("InputDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Color comment");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon.png"));
        stage.showAndWait();
    }

    public void btnOkClicked()
    {
        System.out.println("ok clicked");
        if (!txt_comment.getText().equals(""))
        {
            System.out.println(txt_comment.getText());
            MainController.txt_comment = txt_comment.getText();
            stage.close();
        }
        else {
            System.out.println("invalid");
        }
    }

    public void btnCancelClicked()
    {
        System.out.println("cancel clicked");
        stage.close();
    }

}
