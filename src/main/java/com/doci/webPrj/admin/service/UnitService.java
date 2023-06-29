package com.doci.webPrj.admin.service;

import com.doci.webPrj.admin.entity.Unit;

import java.util.List;

public interface UnitService {
    void save(Unit unit);

    List<Unit> findAll();

    void update(Unit unit);

}
