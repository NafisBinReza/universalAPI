package com.nafis.universalapi.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TesterService {

    private static String testString = "Not updated yet";

    public String testPost(){
        return testString;
    }

    public String updateTestResponse(String body){
        testString = body;
        return "Update Successful. Updated to: \n" + testString;
    }

}
