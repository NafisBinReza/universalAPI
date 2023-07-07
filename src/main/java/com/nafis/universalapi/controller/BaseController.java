package com.nafis.universalapi.controller;

import com.nafis.universalapi.model.PostModel;
import com.nafis.universalapi.service.PostService;
import com.nafis.universalapi.service.TesterService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> postAPI(@RequestBody PostModel postModel){
        return new ResponseEntity<>(postService.post(postModel), HttpStatus.OK);
    }

    @PostMapping("/tester")
    public ResponseEntity<Object> testerAPI(){
        return new ResponseEntity<>(testerService.testPost(), HttpStatus.OK);
    }

}
