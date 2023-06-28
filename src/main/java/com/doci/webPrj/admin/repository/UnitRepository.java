package com.doci.webPrj.admin.repository;

import com.doci.webPrj.admin.entity.Unit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnitRepository {
    void save(Unit unit);

    List<Unit> findAll();

    void update(Unit unit);

    Unit findById(int unitId);
}
