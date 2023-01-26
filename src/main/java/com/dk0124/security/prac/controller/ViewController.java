package com.dk0124.security.prac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/myindex")
    public String getIndex(){
        return "index";
    }
}
