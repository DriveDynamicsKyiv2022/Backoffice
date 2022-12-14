package com.griddynamics.backoffice.controller.management;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.service.area.IAreaService;
import com.griddynamics.backoffice.util.BuildingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/manager/area")
public class AreaManagementController {
    private final IAreaService areaService;

    @Autowired
    public AreaManagementController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping
    public AreaDto addArea(@RequestBody AreaDto areaDto) {
        System.out.println("----handling request");
        Area area = BuildingUtils.getEntity(areaDto);
        area = areaService.addArea(area);
        return BuildingUtils.getDto(area);
    }

    @GetMapping("/{id}")
    public AreaDto getArea(@PathVariable String id) {
        Area area = areaService.getArea(id);
        return BuildingUtils.getDto(area);
    }

    @PutMapping
    public AreaDto updateArea(@RequestBody AreaDto areaDto) {
        Area area = BuildingUtils.getEntity(areaDto);
        area = areaService.updateArea(area);
        return BuildingUtils.getDto(area);
    }

    @DeleteMapping("/{id}")
    public boolean deleteArea(@PathVariable String id) {
        return areaService.deleteArea(id);
    }
}
