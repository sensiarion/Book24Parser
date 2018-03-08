package com;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    //for choosing the right row in dates
    private static int selectedDateIndex =0;
    private static boolean dayIsChanged = false;
    private static int dayInc = 0;
    private static File dir = new File(System.getProperty("user.dir"));
    private static Vector<Date> postDates;

    public static void main(String args[]) {

        GUI gui = new GUI();

        postDates = getTimes(0);
        gui.dateList.setListData(postDates);


        //listen to selected row
        gui.dateList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(dayIsChanged){
                    dayIsChanged = false;
                }
                else {
                    selectedDateIndex = gui.dateList.getSelectedIndex();
                    gui.dateTextField.setText(postDates.get(selectedDateIndex).toString());
                    System.out.println("selectedIndex = " + selectedDateIndex);
                }

            }
        });

        //listen to change the row in textField
        gui.dateChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postDates.set(selectedDateIndex,calendarFromString(gui.dateTextField.getText()));
                gui.dateList.updateUI();
            }
        });

        //listen to change the day in textField
        gui.dayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dayInc++;
                dayIsChanged = true;
                postDates = getTimes(dayInc);
                gui.dateList.setListData(postDates);
                gui.dateList.updateUI();
            }
        });

        //global post button
        gui.postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                long postTime =(long) postDates.get(0).getTime()/1000;

                String url = gui.getUrl();

                System.out.println("postTime = " + postTime);
                System.out.println("url = " + url);

                Map<String,String> respose = Book24Parser.getEntity(url);
                String post = respose.get("post");
                String imageUrl = respose.get("imageUrl");

                System.out.println("post = " + post);
                System.out.println("imageUrl = " + imageUrl);

                try {
                    VkSender.post(post,imageUrl,dir,postTime);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                postDates.remove(0);
                gui.dateList.updateUI();


            }
        });

        gui.filePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser(dir);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int ret = fileChooser.showDialog(null, "Выбрать папку для фото");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    dir = fileChooser.getSelectedFile();
                }

            }
        });
    }

    private static Vector<Date> getTimes(int shift){
        Vector<Date> vector = new Vector<>();
        Calendar date = setToStartTime(shift);
        for(int i=0;i<4;i++){
            vector.add(date.getTime());
            setNextTime(date);
        }
        return vector;
    }


    private static Calendar setToStartTime(int shift){
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_WEEK,shift);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        today.set(Calendar.HOUR,20);

        return  today;
    }

    private static void setNextTime(Calendar date){
        date.add(Calendar.HOUR,1);
    }

    private static Date calendarFromString(String stringDate){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

