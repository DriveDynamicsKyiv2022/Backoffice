package com.griddynamics.backoffice.controller;

import com.griddynamics.area.AreaDto;
import com.griddynamics.backoffice.exception.PaginationException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
        if (VariablesUtils.isNull(pageNumber, pageSize)) {
            throw new PaginationException("Page parameters must be specified");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AreaDto> areaDtos = areaService.getAreas(pageable).map(BuildingUtils::getDto);
        MultiValueMap<String, String> headers = RestUtils.configureHttpHeadersForPage(areaDtos, uriComponentsBuilder, "areas");
        return new ResponseEntity<>(areaDtos, headers, HttpStatus.OK);
    }
}
