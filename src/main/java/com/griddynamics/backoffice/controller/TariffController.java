package com.griddynamics.backoffice.controller;

import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.service.tariff.ITariffService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.backoffice.util.RestUtils;
import com.griddynamics.backoffice.util.VariablesUtils;
import com.griddynamics.tariff.TariffDto;
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
@RequestMapping("/tariffs")
public class TariffController {
    private final ITariffService tariffService;

    @Autowired
    public TariffController(ITariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping
    public ResponseEntity<Page<TariffDto>> getTariffs(Integer pageNumber, Integer pageSize, UriComponentsBuilder uriComponentsBuilder) {
        if (VariablesUtils.isNull(pageNumber, pageSize)) {
            throw new PaginationException("Page parameters must be specified");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<TariffDto> tariffDtos = tariffService.getTariffs(pageable).map(BuildingUtils::getDto);
        MultiValueMap<String, String> headers = RestUtils.configureHttpHeadersForPage(tariffDtos, uriComponentsBuilder, "tariffs");
        return new ResponseEntity<>(tariffDtos, headers, HttpStatus.OK);
    }
}
