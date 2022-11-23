package com.griddynamics.backoffice.controller.management;

import com.griddynamics.backoffice.dto.AreaDto;
import com.griddynamics.backoffice.model.Area;
import com.griddynamics.backoffice.service.area.IAreaService;
import com.griddynamics.backoffice.util.BuildingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager/area")
public class AreaManagementController {
    private final IAreaService areaService;

    @Autowired
    public AreaManagementController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping("/add")
    public AreaDto addArea(@RequestBody AreaDto areaDto) {
        Area area = BuildingUtil.getEntity(areaDto);
        area = areaService.addArea(area);
        return BuildingUtil.getDto(area);
    }

    @GetMapping("/{id}")
    public AreaDto getArea(@PathVariable String id) {
        Area area = areaService.getArea(id);
        return BuildingUtil.getDto(area);
    }

    @PutMapping("/update/{id}")
    public AreaDto updateArea(@PathVariable String id, @RequestBody AreaDto areaDto) {
        Area area = BuildingUtil.getEntity(areaDto);
        area = areaService.updateArea(id, area);
        return BuildingUtil.getDto(area);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteArea(@PathVariable String id) {
        return areaService.deleteArea(id);
    }
}
