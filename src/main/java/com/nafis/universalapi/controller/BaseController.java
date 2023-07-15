package com.nafis.universalapi.controller;

import com.nafis.universalapi.model.PostModel;
import com.nafis.universalapi.service.PostService;
import com.nafis.universalapi.service.TesterService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/common")
public class BaseController {

    @Autowired
    PostService postService;

    @Autowired
    TesterService testerService;

    @GetMapping("/getAPI")
    public ResponseEntity<String> getAPI(@RequestParam("url") String url){
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping("/postAPI")
    public ResponseEntity<Object> postAPI(@RequestBody PostModel postModel, @RequestHeader("key") String key) throws IOException {
        return new ResponseEntity<>(postService.post(postModel, key), HttpStatus.OK);
    }

    @PostMapping("/tester")
    public ResponseEntity<Object> testerAPI(
            @RequestBody String requestBody,
            @RequestParam("param1") String param1,
            @RequestParam("param2") String param2,
            @RequestHeader("authorization") String authorizationHeader,
            @RequestHeader("title") String title
    ){
        return new ResponseEntity<>(testerService.testPost(), HttpStatus.OK);
    }

    @PostMapping("/testResUpdate")
    public ResponseEntity<Object> testResponseUpdateAPI(@RequestBody String requestBody){
        return new ResponseEntity<Object>(testerService.updateTestResponse(requestBody), HttpStatus.OK);
    }

}
