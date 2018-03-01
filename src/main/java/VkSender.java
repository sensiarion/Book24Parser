
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class VkSender {

    //please don't use my token for your necessaries, I am too lazy for crypt it 
    static final String ACCESS_USER_TOKEN = "c3a3951a10766d7bf62cce39fc9d369a5368cb4c598c9b0de5a7bfb848cc7c735a26840161c7b5df2de29";
    static final String ACCESS_TOKEN = "a821762541a7e01f041f131e135547cb538dd13ebab510ed3dd0a827b9f5317f25e90f9bad9d0e47cc9c3";
    static final String GROUP_DOMAIN = "bookworld_hm";

    static String OWNER_ID = "-117584600" ;

    public static void post(String text, String photoUri ,long unixTime) throws IOException {


        //составление запроса
        System.out.println(text);

        List<BasicNameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("access_token",ACCESS_USER_TOKEN));
        nvps.add(new BasicNameValuePair("owner_id",OWNER_ID));
        nvps.add(new BasicNameValuePair("form_group","1"));
        nvps.add(new BasicNameValuePair("message", text));
        nvps.add(new BasicNameValuePair("attachments",photoUri));
        //nvps.add(new BasicNameValuePair("publish_date","0")); TODO



        HttpPost post = new HttpPost("https://api.vk.com/method/wall.post");

        post.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);
        System.out.println(response);


    }

    private static String loadToAlbum(File photo){

        return "";
    }

    private static File saveFromBook24(URI photoUri){
        File file = null;

        return file;
    }

    public static String loadPhotoToVk(String Book24Uri){
        URI uri = null;
        try {
            uri = new URI(Book24Uri);
        } catch (URISyntaxException e) {
            System.out.println("не удалось создать ссылку для загрузки из вк \n loadPhotoToVk error");
            e.printStackTrace();
        }
        String AlbumPhotoAdress = loadToAlbum(saveFromBook24(uri));
        return AlbumPhotoAdress;
    }
}
