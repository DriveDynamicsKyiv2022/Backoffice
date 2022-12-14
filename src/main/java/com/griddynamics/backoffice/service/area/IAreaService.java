package com.griddynamics.backoffice.service.area;

import com.griddynamics.backoffice.model.impl.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAreaService {
    Area getArea(String id);

    Area updateArea(Area newArea);

    Page<Area> getAreas(Pageable pageable);

    boolean deleteArea(String id);

    Area addArea(Area area);
}
