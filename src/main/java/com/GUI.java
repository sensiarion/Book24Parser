package com;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

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
    public static Stack<Exception> ErrorStack = new Stack<>();


    GUI(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(new Dimension(500,500));
        this.add(panel);
        setVisible(true);
    }

    public String getUrl(){
        return urlTextField.getText();
    }

}
