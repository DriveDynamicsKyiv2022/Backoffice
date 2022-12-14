package com.griddynamics.backoffice.controller;

import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.backoffice.response.SimpleResponse;
import com.griddynamics.backoffice.service.tariff.ITariffService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.backoffice.util.RestUtils;
import com.griddynamics.tariff.TariffDto;
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
import java.net.URI;

@RestController
@RequestMapping("/tariff")
public class TariffManagementController {
    private final ITariffService tariffService;

    @Autowired
    public TariffManagementController(ITariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping("/{id}")
    public TariffDto getTariff(@PathVariable String id) {
        Tariff tariff = tariffService.getTariff(id);
        return BuildingUtils.getDto(tariff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponse> deleteTariff(@PathVariable String id) {
        tariffService.deleteTariff(id);
        return new ResponseEntity<>(SimpleResponse.of("Area deleted"), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TariffDto> createTariff(@RequestBody @Valid TariffDto tariffDto, UriComponentsBuilder uriComponentsBuilder) {
        Tariff tariff = BuildingUtils.getEntity(tariffDto);
        tariff = tariffService.addTariff(tariff);
        TariffDto dto = BuildingUtils.getDto(tariff);
        URI uri = RestUtils.buildUri(uriComponentsBuilder, "tariff", tariff.getTariffId());
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public TariffDto updateTariff(@RequestBody @Valid TariffDto tariffDto, @PathVariable String id) {
        Tariff tariff = BuildingUtils.getEntity(tariffDto, id);
        tariff = tariffService.updateTariff(tariff);
        return BuildingUtils.getDto(tariff);
    }
}
