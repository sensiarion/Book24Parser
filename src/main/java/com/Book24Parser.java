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

        Map<String,String> post = new HashMap<>();

        //create Json doc and copying data from url
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
            GUI.ErrorStack.push(e);
        }

        //getting post data
        Elements author;
        try { author =  doc.select("div.rightTD").first().select("a"); }
        catch (NullPointerException e){ author = new Kostile(); }
        Elements title = doc.select("h1.simpleTitle.black");
        Elements description = doc.select("#bookDescription").select("div.text");
        Element image = doc.getElementsByClass("bookImage").first().getElementsByClass("magnifyImage").first();

        String postText = author.text() + " - \"" +title.text()+ "\"" + "\n" + "\n" + description.text() + "\n";
        post.put("post",postText);

        String imageUri = null;
        try {
            System.out.println("imageUri = " + image.attr("src"));
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
