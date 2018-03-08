package com;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JTextField urlTextField;
    public JButton postButton;
    public JList dateList;
    private JPanel panel;
    private JLabel urlLabel;
    public JButton dayButton;
    private JLabel timeLabel;
    public JTextField dateTextField;
    public JButton dateChangeButton;
    public JButton filePathButton;
    public static volatile boolean ParseError = false;


    GUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(new Dimension(500,400));
        this.add(panel);
        setVisible(true);



    }

    private static void parseErrorHandler(){
        //TODO: eror windows generate
        ParseError = false;
    }

    public String getUrl(){
        String url = urlTextField.getText();

        return url;
    }

}
