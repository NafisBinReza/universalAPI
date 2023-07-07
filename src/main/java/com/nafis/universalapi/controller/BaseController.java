package com.nafis.universalapi.controller;

import com.nafis.universalapi.model.PostModel;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
public class BaseController {

    @GetMapping("/getAPI")
    public ResponseEntity<String> getAPI(@RequestParam("url") String url){
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    @PostMapping("/postAPI")
    public ResponseEntity<Object> PostAPI(@RequestBody PostModel postModel){
        return new ResponseEntity<>(postModel, HttpStatus.OK);
    }

}
