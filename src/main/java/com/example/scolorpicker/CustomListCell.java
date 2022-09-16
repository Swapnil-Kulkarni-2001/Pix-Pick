package com.example.scolorpicker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CustomListCell extends ListCell<SamplePOJO>
{
    @FXML
    public Label txt_hex_value;

    @FXML
    public Label txt_color;

    @FXML
    public AnchorPane cell_anchor;

    @FXML
    public Label txt_comment;

    @Override
    protected void updateItem(SamplePOJO color, boolean empty) {
        super.updateItem(color, empty);

        if (color!=null && !empty)
        {
            FXMLLoader mLLoader = new FXMLLoader(getClass().getResource("CustomListCell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();

            }

            txt_hex_value.setText(color.getTxt_hex_color());
            txt_color.setStyle("-fx-background-color: "+color.getTxt_hex_color());
            txt_comment.setText(color.getTxt_comment());
            setText(null);
            setGraphic(cell_anchor);
        }
        else {
            setText(null);
            setGraphic(null);
        }
    }
}
