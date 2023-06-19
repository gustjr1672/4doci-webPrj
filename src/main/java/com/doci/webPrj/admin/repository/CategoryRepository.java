package com.doci.webPrj.admin.repository;

import com.doci.webPrj.admin.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryRepository {
    void save(Category category);

    List<Category> findAll();


    void update(Category category);
}
