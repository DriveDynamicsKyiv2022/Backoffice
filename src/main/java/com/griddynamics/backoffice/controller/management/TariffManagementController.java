package com.griddynamics.backoffice.controller.management;

import com.griddynamics.backoffice.dto.TariffDto;
import com.griddynamics.backoffice.model.Tariff;
import com.griddynamics.backoffice.service.tariff.ITariffService;
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
        return BuildingUtil.getDto(tariff);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTariff(@PathVariable String id) {
        return tariffService.deleteTariff(id);
    }

    @PostMapping("/add")
    public TariffDto createTariff(@RequestBody TariffDto tariffDto) {
        Tariff tariff = BuildingUtil.getEntity(tariffDto);
        tariff = tariffService.addTariff(tariff);
        return BuildingUtil.getDto(tariff);
    }

    @PutMapping("/update/{id}")
    public TariffDto updateTariff(@PathVariable String id, @RequestBody TariffDto tariffDto) {
        Tariff tariff = BuildingUtil.getEntity(tariffDto);
        tariff = tariffService.updateTariff(id, tariff);
        return BuildingUtil.getDto(tariff);
    }
}
