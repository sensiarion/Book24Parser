package com;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    //for choosing the right row in dates
    private static int selectedDateIndex =0;
    private static long dayInMills = 86400000;
    private static Vector<String> postDates;

    public static void main(String args[]) throws IOException {


        GUI gui = new GUI();

        String photoPath = "C:\\\\Users\\ДНС\\Desktop\\temp.png";

//        Map<String,String> post = Book24Parser.getEntity("https://book24.ru/product/istoriya-rossiyskogo-gosudarstva-tsar-petr-alekseevich-aziatskaya-evropeizatsiya-1747176/?block=readers_choice");
        //HttpApacheHandler.getImages("https://book24.ru/upload/resize_cache/iblock/548/600_800_1/548851a5b3dd365bd70a40a82ed1dcb3.png?utm_source=advcake&advcake_params=7wAZ1MM0s5ZAdsH&advcake=1&utm_medium=cpa&utm_campaign=cityads&utm_content=5zcW&utm_term=5zcW",photoPath);
        //VkSender.UploadToServer(VkSender.getUploadServer(VkSender.ALBUM_ID,VkSender.GROUP_ID),"C:\\\\Users\\ДНС\\Desktop\\temp.png");
        //VkSender.UploadPhotoToAlbum(photoPath,VkSender.ALBUM_ID,VkSender.GROUP_ID);

        //VkSender.post(post,"",0,"C:\\\\Users\\ДНС\\Desktop\\temp.png");

        //Calendar is too complicated for using (and by himself it's a big mistake)

        postDates = getTimesVector(new Date());
        gui.dateList.setListData(postDates);


        //listen to selected row
        gui.dateList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                gui.dateTextField.setText(postDates.get(gui.dateList.getSelectedIndex()));
                selectedDateIndex = gui.dateList.getSelectedIndex();
            }
        });

        //listen to change the row in textField
        gui.dateChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postDates.set(selectedDateIndex,gui.dateTextField.getText());
                gui.dateList.updateUI();
            }
        });

        //listen to change the day in textField
        gui.dayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postDates = getTimesVector(new Date(new Date().getTime()+dayInMills));
                gui.dateList.setListData(postDates);
                dayInMills+=86400000;
                gui.dateList.updateUI();
            }
        });

        gui.postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //date doesn't contain year
                int postTime = formatedDatetoUnix(postDates.get(0));
                Date corruptedUnix = new Date(postTime);
                Date normalUnix = new Date();
                normalUnix.setHours(corruptedUnix.getHours());
                normalUnix.setMinutes(corruptedUnix.getMinutes());
                normalUnix.setSeconds(corruptedUnix.getSeconds());

                String url = gui.getUrl();

                System.out.println("postTime = " + normalUnix.getTime());
                System.out.println("url = " + url);

                Map<String,String> respose = Book24Parser.getEntity(url);
                String post = respose.get("post");
                String imageUrl = respose.get("imageUrl");

                System.out.println("post = " + post);
                System.out.println("imageUrl = " + imageUrl);

                //TODO: фикс времени (чекнуть правильная ли дата получается) и определиться с передачей времени контакту
                try {
                    VkSender.post(post,imageUrl,photoPath,(int)(normalUnix.getTime()/1000));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                postDates.remove(0);
                gui.dateList.updateUI();

            }
        });


    }

    private static Vector<String> getTimesVector(Date date) {
        Vector<String> vector = new Vector<>();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM HH:mm");
        date.setHours(20);
        date.setMinutes(0);
        date.setSeconds(0);

        for (int i = 1; i < 5; i++) {
            vector.add(dateFormat.format(date));
            date.setHours(20 + i);
        }

        return vector;
    }

    private static String formatDate (Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM HH:mm");
        return dateFormat.format(date);
    }

    private static int formatedDatetoUnix(String date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM HH:mm");

        try {
            return (int) (dateFormat.parse(date).getTime()/1000);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Не удалось преобразовать указанное вами время, установите его в соответствии с стартовым вариантом оформления");

        }
        return 0;
    }
}

