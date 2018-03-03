
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class VkSender {

    //please don't use my token for your necessaries, I am too lazy for crypt it
    static final String ACCESS_USER_TOKEN = "2faadb189892eb4dec2402a33bb60d9198758d2ffbce9f9bf329aa36a05cbb204cb7279055565c2450404";
    static final String ACCESS_TOKEN = "a821762541a7e01f041f131e135547cb538dd13ebab510ed3dd0a827b9f5317f25e90f9bad9d0e47cc9c3";
    static final String GROUP_DOMAIN = "bookworld_hm";
    static final String OWNER_ID = "-117584600" ;
    static final String USER_ID = "89548778";

    public static void post(String text, String photoUri ,long unixTime) throws IOException {


        //составление запроса
        System.out.println(text);

        List<BasicNameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("access_token",ACCESS_USER_TOKEN));
        nvps.add(new BasicNameValuePair("owner_id",OWNER_ID));
        nvps.add(new BasicNameValuePair("from_group","1"));
        nvps.add(new BasicNameValuePair("message", text));
        //nvps.add(new BasicNameValuePair("attachments",""));
        //nvps.add(new BasicNameValuePair("publish_date","0")); TODO
        nvps.add(new BasicNameValuePair("v","5.73"));

        CloseableHttpResponse response = HttpApacheHandler.getResponseFromPostType("https://api.vk.com/method/wall.post",nvps);
        System.out.println(HttpApacheHandler.getResponseEntity(response,false));


    }
}
