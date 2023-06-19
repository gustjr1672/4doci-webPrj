package com.doci.webPrj.admin.service;

import com.doci.webPrj.admin.entity.Category;

import java.util.List;

public interface CategoryService {
    void save(Category category);

    List<Category> findAll();

    void update(Category category);

}
