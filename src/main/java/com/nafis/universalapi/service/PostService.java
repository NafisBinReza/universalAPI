package com.nafis.universalapi.service;

import com.nafis.universalapi.model.PostModel;
import com.nafis.universalapi.model.common.Params;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    public Object post(PostModel postModel, String key) {
        Object response = callPostAPI(postModel.getUrl(), postModel.getParamsList(), postModel.getHeaderList(), postModel.getBody());
        return getValueFromKey(response, key);
    }

    private Object getValueFromKey(Object target, String key) {
        JSONTokener tokener = new JSONTokener(target.toString());
        Object nestedValue = "404 Not Found";
        if(isValidJson(tokener)){
            JSONObject jsonObject = new JSONObject(target.toString());
            String[] keyParts = key.split("\\.");
            nestedValue = traverseNestedObject(jsonObject, keyParts);

            if (nestedValue != null) {
                System.out.println("Nested Value: " + nestedValue.toString());
            } else {
                System.out.println("Key not found");
            }
        }
        return nestedValue.toString();
    }

    private boolean isValidJson(JSONTokener tokener) {
        try {
            // Attempt to parse the JSON string
            tokener.nextValue();
            return true;
        } catch (Exception e) {
            // JSON parsing failed
            return false;
        }
    }

    private Object traverseNestedObject(JSONObject jsonObject, String[] keyParts) {
        Object nestedValue = jsonObject;

        for (String key : keyParts) {
            if (nestedValue instanceof JSONObject) {
                JSONObject nestedObject = (JSONObject) nestedValue;
                if (nestedObject.has(key)) {
                    nestedValue = nestedObject.get(key);
                } else {
                    return null;
                }
            } else if (nestedValue instanceof JSONArray) {
                JSONArray nestedArray = (JSONArray) nestedValue;
                nestedValue = getFieldValuesFromJSONArray(nestedArray, key);
            } else {
                return null;
            }
        }

        return nestedValue;
    }

    private static Object getFieldValuesFromJSONArray(JSONArray jsonArray, String fieldName) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.has(fieldName)) {
                return jsonObject.get(fieldName);
            }
        }
        return null;
    }

    private Object callPostAPI(String url, List<Params> paramsList, List<Params> headerList, Map<String, Object> requestBody) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String responseBody = "";
        url = url + "?";
        try {
            // Set additional request parameters
            for (Params parameter : paramsList) {
                url = url + parameter.getKey() + "=" + parameter.getValue() + "&";
            }
            url = url.substring(0, url.lastIndexOf("&"));

            // Create the POST request object
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(requestBody.toString());
            httpPost.setEntity(entity);

            // Set headers
            for (Params header : headerList) {
                httpPost.addHeader(header.getKey(), header.getValue());
            }

            // Execute the request and get the response
            HttpResponse response = httpClient.execute(httpPost);

            // Get the response body as a string
            HttpEntity responseEntity = response.getEntity();
            responseBody = EntityUtils.toString(responseEntity);

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
        return responseBody;
    }

}
