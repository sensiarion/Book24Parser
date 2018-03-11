package com;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class HttpConnectionAgent {

    public static HttpResponse connectResponse(URIBuilder uriBuilder) {

        URI uri = null;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            GUI.ErrorStack.push(e);
        }
        HttpClient   client     = HttpClientBuilder.create().build();
        HttpGet      request    = new HttpGet(uri);
        HttpResponse response   = null;

        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            GUI.ErrorStack.push(e);
        }
        return response;
    }
}
