package com.griddynamics.backoffice.controller.user;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.service.area.IAreaService;
import com.griddynamics.backoffice.util.BuildingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/areas")
public class AreaController {
    private final IAreaService areaService;

    @Autowired
    public AreaController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public Page<AreaDto> getAreas(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Area> areas = areaService.getAreas(pageable);
        return areaService.getAreas(pageable).map(BuildingUtils::getDto);
    }
}
