package kr.boot.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class helloController {
    @GetMapping("/")
    public @ResponseBody String hello(){
        return "안녕~";
    }
}
