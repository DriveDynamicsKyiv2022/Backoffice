package com.griddynamics.backoffice.controller.user;

import com.griddynamics.backoffice.dao.tariff.impl.PostgresTariffRepository;
import com.griddynamics.backoffice.dto.TariffDto;
import com.griddynamics.backoffice.model.Tariff;
import com.griddynamics.backoffice.util.BuildingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/tariffs")
public class TariffController {
    private final PostgresTariffRepository postgresTariffRepository;

    @Autowired
    public TariffController(PostgresTariffRepository postgresTariffRepository) {
        this.postgresTariffRepository = postgresTariffRepository;
    }

    @GetMapping
    public List<TariffDto> getTariffs(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Tariff> tariffs = postgresTariffRepository.getTariffsPaginated(pageable);
        return tariffs.stream().map(BuildingUtil::getDto).toList();
    }
}
