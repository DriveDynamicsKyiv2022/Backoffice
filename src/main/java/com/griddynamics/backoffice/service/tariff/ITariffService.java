package com.griddynamics.backoffice.service.tariff;

import com.griddynamics.backoffice.model.impl.Tariff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITariffService {
    Tariff getTariff(String id);

    Page<Tariff> getTariffs(Pageable pageable);

    Tariff addTariff(Tariff tariff);

    Tariff updateTariff(Tariff newTariff);

    boolean deleteTariff(String id);
}
