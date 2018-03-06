package com;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class HttpApacheHandler {

    public static String getResponseEntity(CloseableHttpResponse response, boolean inLineReturn) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String returnString ="";
        String responseLine;

        if(inLineReturn){
            while ((responseLine = reader.readLine()) != null)
               returnString+= responseLine;
        }
        else{
            while ((responseLine = reader.readLine()) != null)
                returnString+= responseLine + "\n";
        }

        reader.close();
        responseLine = null;

        return  returnString;
    }

    public static String getResponseEntity(HttpResponse response, boolean inLineReturn) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String returnString ="";
        String responseLine;

        if(inLineReturn){
            while ((responseLine = reader.readLine()) != null)
                returnString+= responseLine;
        }
        else{
            while ((responseLine = reader.readLine()) != null)
                returnString+= responseLine + "\n";
        }

        reader.close();
        responseLine = null;

        return  returnString;
    }

    public static JSONObject getResponseEntity(HttpResponse response) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String temp;
        String stringResponse = "";
        while ((temp=reader.readLine())!=null)
            stringResponse+=temp;

        JSONObject returnJson = new JSONObject(stringResponse);

        reader.close();
        return  returnJson;
    }

    public static CloseableHttpResponse getResponseFromPostType (String uri, List<BasicNameValuePair> arguments) throws IOException {

        HttpPost post = new HttpPost(uri);

        post.setEntity(new UrlEncodedFormEntity(arguments,"UTF-8"));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);

        return response;
    }

    public static HttpResponse getResponseFromGetType (String uri, List<BasicNameValuePair> arguments) throws IOException {

        URIBuilder builder = null;
        try {
            builder = new URIBuilder(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for(BasicNameValuePair unit : arguments) {
            builder.addParameter(unit.getName(), unit.getValue());
        }

        HttpResponse response = HttpConnectionAgent.connectResponse(builder);

        return response;
    }

    public static void getImages(String src,String photoPath) throws IOException{

        URL url = new URL(src);
        InputStream in = url.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(photoPath));

        for (int i; (i = in.read()) != -1;)
            out.write(i);
        in.close();
        out.close();
    }
}
