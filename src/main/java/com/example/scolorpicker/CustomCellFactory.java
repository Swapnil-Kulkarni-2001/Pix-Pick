package com.example.scolorpicker;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CustomCellFactory implements Callback<ListView<SamplePOJO>, ListCell<SamplePOJO>>
{
    @Override
    public ListCell<SamplePOJO> call(ListView<SamplePOJO> param) {
        return new CustomListCell();
    }
}
