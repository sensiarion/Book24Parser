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

public class VkSender implements VkSettings {

    /*  almost all method require default data for working with Vk API
        it's contains in interface
            ACCESS_USER_TOKEN
            ACCESS_TOKEN
            GROUP_DOMAIN - group name from url
            OWNER_ID - differ from GROUP_ID by '-' before
            GROUP_ID
            USER_ID
            ALBUM_ID

    */

    //load post to Vk with your text at "unixTime",picture from internet that will be saved in "dir" path
    public static void post(String text, String photoUri,File dir ,long unixTime){

        String photoPath = dir.getAbsolutePath()+"/tempPhoto.png";

        System.out.println("unixTime in VkSender.post = " + unixTime);
        String attachments = "";
        if(photoUri!=""){
            try{
             HttpApacheHandler.getImages(photoUri,photoPath);
             attachments =  UploadPhotoToAlbum(photoPath,ALBUM_ID,GROUP_ID);
            }
            catch (IOException e){
                e.printStackTrace();
                GUI.ErrorStack.push(e);
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

        CloseableHttpResponse response = null;
        try {
            response = HttpApacheHandler.getResponseFromPostType("https://api.vk.com/method/wall.post",nvps);
            System.out.println(HttpApacheHandler.getResponseEntity(response,false));
        } catch (IOException e) {
            e.printStackTrace();
            GUI.ErrorStack.push(e);
        }
    }

    //TODO: Queue for photos (for(i<5))
    //load photo to the album in selected group
    private static String UploadPhotoToAlbum(String photoPath,String albumId,String groupId) throws IOException {
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

    //get upload server for download photo to Vk
    private static String getUploadServer(String albumId,String groupId) throws IOException {
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
    //load selected photo to server
    private static JSONObject UploadToServer(String uploadUrl, String photoPath){

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
            GUI.ErrorStack.push(e);
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