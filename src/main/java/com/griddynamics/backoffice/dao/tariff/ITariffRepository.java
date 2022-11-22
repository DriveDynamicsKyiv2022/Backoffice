package com.griddynamics.backoffice.dao.tariff;

import com.griddynamics.backoffice.model.Tariff;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITariffRepository {
    List<Tariff> getTariffsPaginated(Pageable pageable);

    Tariff addTariff(Tariff tariff);

    boolean deleteTariff(long id);

    Tariff getTariff(long id);

    Tariff updateTariff(long id, Tariff newTariff);
}
