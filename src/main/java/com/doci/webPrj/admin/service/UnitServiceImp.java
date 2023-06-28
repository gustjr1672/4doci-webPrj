package com.doci.webPrj.admin.service;

import com.doci.webPrj.admin.entity.Unit;
import com.doci.webPrj.admin.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImp implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Override
    public void save(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public void update(Unit unit) {
        unitRepository.update(unit);
    }

    @Override
    public Unit getUnit(int unitId) {
        Unit unit = unitRepository.findById(unitId);
        return unit;
    }
}
