package com.griddynamics.backoffice.controller.management;

import com.griddynamics.backoffice.dao.area.IAreaRepository;
import com.griddynamics.backoffice.dto.AreaDto;
import com.griddynamics.backoffice.model.Area;
import com.griddynamics.backoffice.util.BuildingUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final IAreaRepository areaRepository;

    @Autowired
    public AreaManagementController(IAreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @PostMapping("/add")
    public AreaDto addArea(@RequestBody AreaDto areaDto) {
        Area area = BuildingUtil.getEntity(areaDto);
        area = areaRepository.addArea(area);
        return BuildingUtil.getDto(area);
    }

    @GetMapping("/{id}")
    public AreaDto getArea(@PathVariable String id) {
        Area area = areaRepository.getArea(id);
        return BuildingUtil.getDto(area);
    }

    @PutMapping("/update/{id}")
    public AreaDto updateArea(@PathVariable String id, @RequestBody AreaDto areaDto) {
       return null;
    }
}
