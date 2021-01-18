package com.dwy.logistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: DongWenYu
 * @Date: 2021/1/12 14:39
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/routeDraw")
    public String routeDraw(){
        return "routeDraw";
    }

}
