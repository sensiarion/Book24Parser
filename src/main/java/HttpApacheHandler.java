import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.URL;
import java.util.List;

public class HttpApacheHandler {
    //TODO: toJson func, Json alike String responce
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

    public static CloseableHttpResponse getResponseFromPostType (String uri, List<BasicNameValuePair> arguments) throws IOException {

        HttpPost post = new HttpPost(uri);

        post.setEntity(new UrlEncodedFormEntity(arguments,"UTF-8"));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);

        return response;
    }

    public static void getImages(String src) throws IOException{

        URL url = new URL(src);
        InputStream in = url.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\\\Users\\ДНС\\Desktop\\temp.png"));

        for (int i; (i = in.read()) != -1;)
            out.write(i);
        in.close();
        out.close();
    }
}
