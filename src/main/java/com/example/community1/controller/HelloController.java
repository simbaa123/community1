package com.example.community1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name="name") String name, Model model) {
        // 将 name 属性添加到模型中
        model.addAttribute("name", name);
        // 返回模板名称（不包括 .html 扩展名）
        return "hello";
    }
}
