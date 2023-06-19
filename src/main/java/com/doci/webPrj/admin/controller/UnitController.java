package com.doci.webPrj.admin.controller;

import com.doci.webPrj.admin.entity.Category;
import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/unit")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping("manage")
    public String manage() {
        return "/admin/unit/manage";
    }

    @GetMapping("register")
    public String register() {
        return "/admin/unit/register";
    }

    @PostMapping("register")
    public String register(Unit unit) {
        unitService.save(unit);
        return "redirect:/admin/unit/manage";
    }

    @GetMapping("edit")
    public String edit(Model model) {
        List<Unit> unitList = unitService.findAll();
        model.addAttribute("unitList", unitList);
        return "admin/unit/edit";
    }

    @PostMapping("edit")
    public String edit(Unit unit) {
        unitService.update(unit);
        return "redirect:/admin/unit/manage";
    }
}
