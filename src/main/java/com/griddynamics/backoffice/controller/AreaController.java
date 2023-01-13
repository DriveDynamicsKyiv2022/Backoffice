package com.griddynamics.backoffice.controller;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.model.impl.Area;
import com.griddynamics.backoffice.response.SimpleResponse;
import com.griddynamics.backoffice.service.area.IAreaService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.backoffice.util.RestUtils;
import com.griddynamics.backoffice.util.VariablesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
import java.net.URI;

@RestController
@RequestMapping("/areas")
public class AreaController {
    private final IAreaService areaService;

    @Autowired
    public AreaController(IAreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public ResponseEntity<Page<AreaDto>> getAreas(Integer pageNumber, Integer pageSize, UriComponentsBuilder uriComponentsBuilder) {
        if (VariablesUtils.notAllSpecified(pageNumber, pageSize)) {
            throw new PaginationException("Page parameters must be specified");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AreaDto> areaDtos = areaService.getAreas(pageable).map(BuildingUtils::getDto);
        MultiValueMap<String, String> headers = RestUtils.configureHttpHeadersForPage(areaDtos, uriComponentsBuilder, "areas");
        return new ResponseEntity<>(areaDtos, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AreaDto> addArea(@RequestBody @Valid AreaDto areaDto, UriComponentsBuilder uriComponentsBuilder) {
        Area area = BuildingUtils.getEntity(areaDto);
        area = areaService.addArea(area);
        AreaDto receivedAreaDto = BuildingUtils.getDto(area);
        URI responseUri = RestUtils.buildUri(uriComponentsBuilder, "areas", receivedAreaDto.getAreaId());
        return ResponseEntity.created(responseUri)
                .body(receivedAreaDto);
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
