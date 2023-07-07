package com.nafis.universalapi.service;

import com.nafis.universalapi.model.PostModel;
import com.nafis.universalapi.model.common.Params;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    public Object post(PostModel postModel){
        return callPostAPI(postModel.getUrl(), postModel.getParamsList(), postModel.getHeaderList(), postModel.getBody());
    }

    private Object callPostAPI(String url, List<Params> paramsList, List<Params> headerList, JSONObject requestBody){
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpEntity responseEntity = null;
        try {
            // Create the POST request object
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(requestBody.toString());
            httpPost.setEntity(entity);

            // Set headers
            for (Params header : headerList) {
                httpPost.setHeader(new BasicHeader(header.getKey(), header.getValue()));
            }

            // Set additional request parameters
            List<NameValuePair> params = new ArrayList<>();
            for (Params parameter : paramsList) {
                params.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpPost);

            // Get the response body as a string
            responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity);

            // Handle the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                System.out.println("Request successful");
                System.out.println("Response: " + responseBody);
            } else {
                System.out.println("Request failed with status code: " + statusCode);
                System.out.println("Response: " + responseBody);
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return responseEntity;
    }

}
