package com.devtest.devtest.controller;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class DevController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/home")
        public String Hello(){

        return "연결성공";
    }
}
