package com.griddynamics.backoffice.controller.management;

import com.griddynamics.backoffice.dao.tariff.impl.PostgresTariffRepository;
import com.griddynamics.backoffice.dto.TariffDto;
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
    PostgresTariffRepository postgresTariffRepository;

    @Autowired
    public TariffManagementController(PostgresTariffRepository postgresTariffRepository) {
        this.postgresTariffRepository = postgresTariffRepository;
    }



    @GetMapping("/{id}")
    public TariffDto getTariff(@PathVariable int id) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTariff(@PathVariable int id) {
        return true;
    }

    @PostMapping("/add")
    public TariffDto createTariff(@RequestBody TariffDto tariffDto) {
        return null;
    }

    @PutMapping("/update/{id}")
    public TariffDto updateTariff(@PathVariable long id, @RequestBody TariffDto tariffDto) {
        return null;
    }
}
