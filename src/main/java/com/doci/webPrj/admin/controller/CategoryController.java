package com.doci.webPrj.admin.controller;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("manage")
    public String manage() {
        return "/admin/category/manage";
    }

    @GetMapping("register")
    public String register(){
        return "/admin/category/register";
    }

    @PostMapping("register")
    public String register(Category category){
        categoryService.save(category);
        return "redirect:/admin/category/manage";
    }
}
