package com.griddynamics.backoffice.controller.management;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.response.SimpleResponse;
import com.griddynamics.backoffice.service.area.IAreaService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.backoffice.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/area")
public class AreaManagementController {
    private final IAreaService areaService;

    @Autowired
    public AreaManagementController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping
    public ResponseEntity<AreaDto> addArea(@RequestBody @Valid AreaDto areaDto, UriComponentsBuilder uriComponentsBuilder) {
        Area area = BuildingUtils.getEntity(areaDto);
        area = areaService.addArea(area);
        AreaDto receivedArea = BuildingUtils.getDto(area);
        var responseUri = RestUtils.buildUri(uriComponentsBuilder, "area", receivedArea.getAreaId());
        return ResponseEntity.created(responseUri)
                .body(receivedArea);
    }

    @GetMapping("/{id}")
    public AreaDto getArea(@PathVariable String id) {
        Area area = areaService.getArea(id);
        return BuildingUtils.getDto(area);
    }

    @PutMapping("/{id}")
    public AreaDto updateArea(@RequestBody @Valid AreaDto areaDto, @PathVariable String id) {
        Area area = BuildingUtils.getEntity(areaDto, id);
        area = areaService.updateArea(area);
        return BuildingUtils.getDto(area);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> deleteArea(@PathVariable String id) {
        areaService.deleteArea(id);
        return new ResponseEntity<>(SimpleResponse.of("Area deleted"), HttpStatus.OK);
    }
}
