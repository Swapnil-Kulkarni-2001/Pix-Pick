package com.example.scolorpicker;

public class SamplePOJO
{
    public String txt_hex_color;
    public String txt_comment;

    public SamplePOJO(String txt_hex_color, String txt_comment) {
        this.txt_hex_color = txt_hex_color;
        this.txt_comment = txt_comment;
    }

    public String getTxt_hex_color() {
        return txt_hex_color;
    }

    public void setTxt_hex_color(String txt_hex_color) {
        this.txt_hex_color = txt_hex_color;
    }

    public String getTxt_comment() {
        return txt_comment;
    }

    public void setTxt_comment(String txt_comment) {
        this.txt_comment = txt_comment;
    }
}
