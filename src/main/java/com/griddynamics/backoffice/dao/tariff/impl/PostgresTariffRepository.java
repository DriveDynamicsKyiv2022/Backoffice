package com.griddynamics.backoffice.dao.tariff.impl;

import com.griddynamics.backoffice.dao.tariff.IPostgresTariffRepository;
import com.griddynamics.backoffice.dao.tariff.ITariffRepository;
import com.griddynamics.backoffice.model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostgresTariffRepository implements ITariffRepository {
    private final IPostgresTariffRepository tariffRepository;

    @Autowired
    public PostgresTariffRepository(IPostgresTariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    @Override
    public List<Tariff> getTariffsPaginated(Pageable pageable) {
        return tariffRepository.findAll(pageable).stream().toList();
    }

    @Override
    public Tariff addTariff(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    @Override
    public boolean deleteTariff(long id) {
        try {
            tariffRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public Tariff getTariff(long id) {
        return tariffRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such Tariff"));
    }

    @Override
    public Tariff updateTariff(long id, Tariff newTariff) {
        Integer r = tariffRepository.updateById(id, newTariff.getName(), newTariff.getRatePerHour(),
                newTariff.getCarBodyStyle().name(), newTariff.getDescription());
        return tariffRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such Tariff"));
    }
}
