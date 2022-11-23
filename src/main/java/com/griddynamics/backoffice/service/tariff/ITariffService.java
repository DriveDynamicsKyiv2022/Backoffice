package com.griddynamics.backoffice.service.tariff;

import com.griddynamics.backoffice.model.Tariff;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITariffService {
    Tariff getTariff(String id);

    List<Tariff> getTariffs(Pageable pageable);

    Tariff addTariff(Tariff tariff);

    Tariff updateTariff(String id, Tariff newTariff);

    boolean deleteTariff(String id);
}
