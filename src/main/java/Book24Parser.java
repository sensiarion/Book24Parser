import org.apache.http.cookie.Cookie;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.CookieStore;


public class Book24Parser {

    private static String post;

    public static String getEntity(final String url){

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
        //setting the data into volatile variable

        post =  author.text() + " - \"" +title.text()+ "\"" + "\n" + "\n" + description.text() + "\n";

        String imageUri = "https://book24.ru"+(image.attr("src"));
        System.out.println(imageUri);

        return post;
    }

}
