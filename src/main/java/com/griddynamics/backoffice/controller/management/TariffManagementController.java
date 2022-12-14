package com.griddynamics.backoffice.controller.management;

import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.backoffice.service.tariff.ITariffService;
import com.griddynamics.backoffice.util.BuildingUtils;
import com.griddynamics.tariff.TariffDto;
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
@RequestMapping("/manager/tariff")
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
    public boolean deleteTariff(@PathVariable String id) {
        return tariffService.deleteTariff(id);
    }

    @PostMapping
    public TariffDto createTariff(@RequestBody TariffDto tariffDto) {
        Tariff tariff = BuildingUtils.getEntity(tariffDto);
        tariff = tariffService.addTariff(tariff);
        return BuildingUtils.getDto(tariff);
    }

    @PutMapping
    public TariffDto updateTariff(@RequestBody TariffDto tariffDto) {
        Tariff tariff = BuildingUtils.getEntity(tariffDto);
        tariff = tariffService.updateTariff(tariff);
        return BuildingUtils.getDto(tariff);
    }
}
