package com.example.scolorpicker;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static java.awt.Image.SCALE_DEFAULT;
import static java.lang.Integer.toHexString;
import static javax.swing.SwingConstants.CENTER;


public class MainController implements Initializable {

    @FXML
    public SwingNode swing_node;

    @FXML
    public Label pixel_color;

    @FXML
    public AnchorPane anchor_pane;

    @FXML
    public ToggleSwitch switch_stay_on_top;

    @FXML
    public Label txt_coordinates;

    @FXML
    public Label txt_hex;

    @FXML
    public Label txt_format;

    @FXML
    public ListView<SamplePOJO> color_list;

    @FXML
    public Label txt_sample_area;

    @FXML
    public JFXSlider r_slider;

    @FXML
    public JFXSlider g_slider;

    @FXML
    public JFXSlider b_slider;

    @FXML
    public JFXComboBox<String> color_format;

    public javafx.scene.control.MenuItem menuItem1;
    public javafx.scene.control.MenuItem menuItem2;
    public javafx.scene.control.MenuItem menuItem3;
    public ContextMenu contextMenu;

    @SuppressWarnings("ClassEscapesDefinedScope")
    public ImagePanel imagePanel;

    public Robot robot;
    static final int IMAGE_SIZE = 150;
    private static final int SCREEN_CAPTURED_SIZE = IMAGE_SIZE / 20;
    private static final int SCREEN_CAPTURED_RADIUS = SCREEN_CAPTURED_SIZE / 2;

    int toastMsgTime = 800; //3.5 seconds
    int fadeInTime = 100; //0.5 seconds
    int fadeOutTime = 100; //0.5 seconds

    private int centralX, centralY;

    public static Thread anotherThread;

    public Stage stage;

    public String hexColor;

    public ObservableList<SamplePOJO> observableList;

    public int R, G, B;
    public int staticR, staticG, staticB;

    public static String txt_comment = "";

    public MainController() {
        observableList = FXCollections.observableArrayList();
    }


    public void changeStayOnTop() {
        System.out.println(switch_stay_on_top.isSelected());
        stage = (Stage) anchor_pane.getScene().getWindow();
        stage.setAlwaysOnTop(switch_stay_on_top.isSelected());
    }


    public void onSampleMenuClicked() {
        System.out.println("Sampled");
        //observableList.add(hexColor);
        txt_sample_area.setStyle("-fx-background-color: " + hexColor);
        r_slider.setValue(R);
        g_slider.setValue(G);
        b_slider.setValue(B);
        staticR = R;
        staticG = G;
        staticB = B;
        SamplePOJO color = new SamplePOJO(hexColor, "");
        observableList.add(color);
        //color_list.getItems().add(color);
    }

    public void onCopyToClickBoardMenuClicked() {
        System.out.println("Copied");
        StringSelection stringSelection = new StringSelection(hexColor);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Toast.makeText(stage, "Color code copied", toastMsgTime, fadeInTime, fadeOutTime);

    }

    public void clearSampleListMenuClicked() {
        observableList.clear();
        //color_list.getItems().clear();
        color_list.setCellFactory(new CustomCellFactory());
        System.out.println("cleared");
    }

    public void listItemClicked() {
        System.out.println("clicked on " + color_list.getSelectionModel().getSelectedItem());
    }

    public void aboutMenuClicked() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("PIX-pick");
        dialog.setHeaderText("About");
        dialog.setContentText("Version : 1.0\nLicense : freeware\nRepo : www.google.com");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }


    public void colorFormatComboBoxClicked()
    {
        System.out.println(color_format.getSelectionModel().getSelectedItem());
        showColorFormat();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            robot = new Robot(getLocalGraphicsEnvironment().getDefaultScreenDevice());
        } catch (AWTException e) {
            e.printStackTrace();
        }

        contextMenu = new ContextMenu();

        menuItem1 = new javafx.scene.control.MenuItem("Copy color");
        menuItem2 = new javafx.scene.control.MenuItem("Delete");
        menuItem3 = new javafx.scene.control.MenuItem("Comment");



        menuItem1.setOnAction(event -> {
            SamplePOJO color = color_list.getSelectionModel().getSelectedItem();
            if (color!=null)
            {
                StringSelection stringSelection = new StringSelection(color.getTxt_hex_color());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                Toast.makeText(stage,"Color code copied", toastMsgTime, fadeInTime, fadeOutTime);
            }
        });

        menuItem2.setOnAction(event -> {
            SamplePOJO color = color_list.getSelectionModel().getSelectedItem();
            if(color!=null)
            {
//                color_list.getItems().remove(color_list.getSelectionModel().getSelectedItem());
                observableList.remove(color_list.getSelectionModel().getSelectedItem());
                color_list.setCellFactory(new CustomCellFactory());
                color_list.refresh();
            }
        });

        menuItem3.setOnAction(event -> {
            SamplePOJO color = color_list.getSelectionModel().getSelectedItem();
            if (color!=null)
            {
                try {
                    InputDialog.display();
                    if (!txt_comment.equals(""))
                    {
                        System.out.println("correct");
                        int index = color_list.getSelectionModel().getSelectedIndex();
                        System.out.println(index);
                        SamplePOJO old = color_list.getItems().get(index);
                        //color_list.getItems().remove(index);
                        color_list.getItems().set(index,new SamplePOJO(old.getTxt_hex_color(),txt_comment));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        contextMenu.getItems().addAll(menuItem1,menuItem3,menuItem2);

        color_list.setContextMenu(contextMenu);
        color_list.setItems(observableList);
        color_list.setCellFactory(new CustomCellFactory());

        r_slider.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::rSliderValueChanged);
        g_slider.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::gSliderValueChanged);
        b_slider.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::bSliderValueChanged);

        switch_stay_on_top.setSelected(false);


        color_format.getItems().addAll("HEX","RGB","HSV","HSL");

        color_format.getSelectionModel().select("RGB");

        imagePanel = new ImagePanel("", CENTER,"");

        createSwingContent(swing_node);

        runAnotherThread();

        update();

    }


    private <T extends Event> void bSliderValueChanged(T t) {
        txt_sample_area.setStyle("-fx-background-color: rgb(" + r_slider.getValue() + "," + g_slider.getValue() + ", " + b_slider.getValue() + ");");
    }

    private <T extends Event> void gSliderValueChanged(T t) {
        txt_sample_area.setStyle("-fx-background-color: rgb(" + r_slider.getValue() + "," + g_slider.getValue() + ", " + b_slider.getValue() + ");");
    }

    private <T extends Event> void rSliderValueChanged(T t)
    {
        txt_sample_area.setStyle("-fx-background-color: rgb(" + r_slider.getValue() + "," + g_slider.getValue() + ", " + b_slider.getValue() + ");");
    }


    private void update() {
        final Point centralPoint = MouseInfo.getPointerInfo().getLocation();
        centralX = (int) centralPoint.getX();
        centralY = (int) centralPoint.getY();

        imagePanel.setIcon(new ImageIcon(captureScreen()));

        txt_coordinates.setText("x : "+centralX+" y : "+centralY);

        final Color pixelColor = getColorAndDoTask();

        txt_hex.setText("Hex code : #" + toHexString(pixelColor.getRGB()).substring(2));
//        txt_red.setText("R : "+pixelColor.getRed());
//
//        txt_green.setText("G : "+pixelColor.getGreen());
//        txt_blue.setText("B : "+pixelColor.getBlue());
        showColorFormat();
        pixel_color.setStyle("-fx-background-color: #" +toHexString(pixelColor.getRGB()).substring(2));

        System.out.println("#" + toHexString(pixelColor.getRGB()).substring(2));

    }

    private Image captureScreen() {
        final Rectangle rectangle = new Rectangle(centralX - SCREEN_CAPTURED_RADIUS, centralY - SCREEN_CAPTURED_RADIUS, SCREEN_CAPTURED_SIZE, SCREEN_CAPTURED_SIZE);
        return robot.createScreenCapture(rectangle).getScaledInstance(150, 150, SCALE_DEFAULT);
    }

    public void createSwingContent(SwingNode swingNode)
    {
        SwingUtilities.invokeLater(() -> swingNode.setContent(imagePanel));
    }



    @SuppressWarnings("BusyWait")
    void runAnotherThread()
    {
        anotherThread = new Thread(() -> {
            while (true)
            {
                PointerInfo mousePointer = MouseInfo.getPointerInfo();
                if (mousePointer == null) {
                    Thread.yield();
                } else {
                    final Point centralPoint = MouseInfo.getPointerInfo().getLocation();
                    centralX = (int) centralPoint.getX();
                    centralY = (int) centralPoint.getY();
                    Platform.runLater(() -> {
                        imagePanel.setIcon(new ImageIcon(captureScreen()));
                        txt_coordinates.setText("x : "+centralX+"     y : "+centralY);
                        final Color pixelColor = getColorAndDoTask();
                        txt_hex.setText("Hex code : #" + toHexString(pixelColor.getRGB()).substring(2));
//                        txt_red.setText("R : "+pixelColor.getRed());
//                        txt_green.setText("G : "+pixelColor.getGreen());
//                        txt_blue.setText("B : "+pixelColor.getBlue());
                        showColorFormat();
                        pixel_color.setStyle("-fx-background-color: #" +toHexString(pixelColor.getRGB()).substring(2));

                    });

                    try {
                        Thread.sleep(50L);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        anotherThread.start();
    }


    public Color getColorAndDoTask()
    {
        Color pixelColor = robot.getPixelColor(centralX, centralY);
        hexColor = "#"+toHexString(pixelColor.getRGB()).substring(2);
        R = pixelColor.getRed();
        G = pixelColor.getGreen();
        B = pixelColor.getBlue();
        return pixelColor;
    }

    public void showColorFormat()
    {
        String format = color_format.getSelectionModel().getSelectedItem();
        if(format!=null)
        {
            switch (format) {
                case "HEX" -> txt_format.setText(hexColor);
                case "RGB" -> txt_format.setText(getRGB());
                case "HSV" -> txt_format.setText(rgb_to_hsv(R,G,B));
            }
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public String rgb_to_hsv(double r, double g, double b)
    {

        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        r = r / 255.0;
        g = g / 255.0;
        b = b / 255.0;

        // h, s, v = hue, saturation, value
        double cmax = Math.max(r, Math.max(g, b)); // maximum of r, g, b
        double cmin = Math.min(r, Math.min(g, b)); // minimum of r, g, b
        double diff = cmax - cmin; // diff of cmax and cmin.
        double h = -1, s;

        // if cmax and cmax are equal then h = 0
        if (cmax == cmin)
            h = 0;

            // if cmax equal r then compute h
        else if (cmax == r)
            h = (60 * ((g - b) / diff) + 360) % 360;

            // if cmax equal g then compute h
        else if (cmax == g)
            h = (60 * ((b - r) / diff) + 120) % 360;

            // if cmax equal b then compute h
        else if (cmax == b)
            h = (60 * ((r - g) / diff) + 240) % 360;

        // if cmax equal zero
        if (cmax == 0)
            s = 0;
        else
            s = (diff / cmax) * 100;

        // compute v
        double v = cmax * 100;
        return "HSV("+String.format("%.2f",h)+","+String.format("%.2f",s)+","+String.format("%.2f",v)+")";

    }

    public String getRGB()
    {
        return "RGB("+R+","+G+","+B+")";
    }


}
