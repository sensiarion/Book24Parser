package com;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Book24Parser {



    public static Map<String,String> getEntity(final String url){

        Map<String,String> post = new HashMap<String, String>();

        //create Json doc and copying data from url
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            GUI.ParseError = true;
        }

        //getting post data
        Elements author =  doc.select("div.rightTD").first().select("a");
        Elements title = doc.select("h1.simpleTitle.black");
        Elements description = doc.select("#bookDescription").select("div.text");
        Element image = doc.select("div.image.active").select("img.magnifyImage").first();

        String postText = author.text() + " - \"" +title.text()+ "\"" + "\n" + "\n" + description.text() + "\n";
        post.put("post",postText);

        String imageUri = null;
        try {
            URL imageUrl = new URL(image.attr("src"));
            imageUri = image.attr("src");
        } catch (MalformedURLException e) {
            imageUri = "https://book24.ru"+(image.attr("src"));
        }
        finally {
            post.put("imageUrl",imageUri);
        }

        System.out.println(imageUri);

        return post;
    }

}
