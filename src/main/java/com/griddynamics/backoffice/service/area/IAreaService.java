package com.griddynamics.backoffice.service.area;

import com.griddynamics.backoffice.model.Area;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAreaService {
    Area getArea(String id);

    Area updateArea(String id, Area newArea);

    List<Area> getAreas(Pageable pageable);

    boolean deleteArea(String id);

    Area addArea(Area area);
}
