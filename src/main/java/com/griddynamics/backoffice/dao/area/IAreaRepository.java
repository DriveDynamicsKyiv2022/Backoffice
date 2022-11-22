package com.griddynamics.backoffice.dao.area;

import com.griddynamics.backoffice.model.Area;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAreaRepository {
    Area addArea(Area area);
    Area getArea(String id);
    Area updateArea(String id, Area newArea);
    boolean deleteArea(String id);
    List<Area> getAreas(Pageable pageable);
}
