package com;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class VkSender {

    //please don't use my token for your necessaries, I am too lazy for crypt it or hide in ENUM
    static final String ACCESS_USER_TOKEN = "ae82396cb24f68048c5a94955006747f3ff25916e7b8e2a8074f5a687110cb2f600d5753e6efdfc761d12";
    static final String ACCESS_TOKEN = "a821762541a7e01f041f131e135547cb538dd13ebab510ed3dd0a827b9f5317f25e90f9bad9d0e47cc9c3";
    static final String GROUP_DOMAIN = "bookworld_hm";
    static final String OWNER_ID = "-117584600" ;
    static final String GROUP_ID = "117584600";
    static final String USER_ID = "89548778";
    static final String ALBUM_ID = "252164558";

    public static void post(String text, String photoUri,String photoPath ,int unixTime) throws IOException {

        System.out.println("unixTime in VkSender.post = " + unixTime);
        String attachments = "";
        if(photoUri!=""){
            try{
             HttpApacheHandler.getImages(photoUri,photoPath);
             attachments =  UploadPhotoToAlbum(photoPath,ALBUM_ID,GROUP_ID);
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("Не удалось загрузить фото");
            }
        }

        List<BasicNameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("access_token",ACCESS_USER_TOKEN));
        nvps.add(new BasicNameValuePair("owner_id", OWNER_ID));
        nvps.add(new BasicNameValuePair("from_group","1"));
        nvps.add(new BasicNameValuePair("message", text));
        nvps.add(new BasicNameValuePair("attachments",attachments));
        nvps.add(new BasicNameValuePair("publish_date",String.valueOf(unixTime)));
        nvps.add(new BasicNameValuePair("v","5.63"));

        CloseableHttpResponse response = HttpApacheHandler.getResponseFromPostType("https://api.vk.com/method/wall.post",nvps);
        System.out.println(HttpApacheHandler.getResponseEntity(response,false));


    }


    //TODO: Queue for photos (for(i<5))
    public static String UploadPhotoToAlbum(String photoPath,String albumId,String groupId) throws IOException {
        String photoInfo ="photo";
        JSONObject jsonPhotoInfo =  UploadToServer(getUploadServer(albumId,groupId),photoPath);
        System.out.println(jsonPhotoInfo.get("server"));
        System.out.println(jsonPhotoInfo.get("photos_list"));
        System.out.println(jsonPhotoInfo.get("hash"));


        List<BasicNameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("access_token",ACCESS_USER_TOKEN));
        nvps.add(new BasicNameValuePair("album_id",albumId));
        nvps.add(new BasicNameValuePair("group_id",groupId));
        nvps.add(new BasicNameValuePair("server",jsonPhotoInfo.get("server").toString()));
        nvps.add(new BasicNameValuePair("photos_list",jsonPhotoInfo.get("photos_list").toString()));
        nvps.add(new BasicNameValuePair("hash",jsonPhotoInfo.get("hash").toString()));
        nvps.add(new BasicNameValuePair("v","5.73"));

        CloseableHttpResponse response = HttpApacheHandler.getResponseFromPostType("https://api.vk.com/method/photos.save",nvps);
        JSONObject photoArray = HttpApacheHandler.getResponseEntity(response);
        JSONObject photoEntity = photoArray.getJSONArray("response").getJSONObject(0);
        int owner_id = photoEntity.getInt("owner_id");
        int id = photoEntity.getInt("id");
        photoInfo+=owner_id+"_"+id;

        return photoInfo;
    }

    public static String getUploadServer(String albumId,String groupId) throws IOException {
        String upload_url;

        List<BasicNameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("access_token",ACCESS_USER_TOKEN));
        nvps.add(new BasicNameValuePair("album_id",albumId));
        nvps.add(new BasicNameValuePair("group_id",groupId));
        nvps.add(new BasicNameValuePair("v","5.73"));

        HttpResponse response = HttpApacheHandler.getResponseFromGetType("https://api.vk.com/method/photos.getUploadServer",nvps);
        org.json.JSONObject responseEntity = HttpApacheHandler.getResponseEntity(response);
        upload_url = responseEntity.getJSONObject("response").getString("upload_url");
        System.out.println("upload_url = " + upload_url);
        return upload_url;
    }

    public static JSONObject UploadToServer(String uploadUrl, String photoPath){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(uploadUrl);

        //transfer photo to multipart/form-data and send it to UploadServer
        FileBody uploadFilePart = new FileBody(new File(photoPath));
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("file1",uploadFilePart);
        post.setEntity(reqEntity);

        CloseableHttpResponse response = null;
        try {
            response =  httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject responseObj = null;
        try {
            responseObj = HttpApacheHandler.getResponseEntity(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseObj;

    }
}