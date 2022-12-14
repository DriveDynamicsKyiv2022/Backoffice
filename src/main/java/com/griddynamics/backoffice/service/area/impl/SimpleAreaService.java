package com.griddynamics.backoffice.service.area.impl;

import com.griddynamics.backoffice.dao.area.IAreaDao;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.service.area.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SimpleAreaService implements IAreaService {
    private final IAreaDao areaDao;

    @Autowired
    public SimpleAreaService(IAreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @Override
    public Area getArea(String id) {
        return areaDao.find(id);
    }

    @Override
    public Area updateArea(Area newArea) {
        return areaDao.updateEntity(newArea);
    }

    @Override
    public Page<Area> getAreas(Pageable pageable) {
        return areaDao.findAll(pageable);
    }

    @Override
    public boolean deleteArea(String id) {
        return areaDao.delete(id);
    }

    @Override
    public Area addArea(Area area) {
        return areaDao.save(area);
    }
}
