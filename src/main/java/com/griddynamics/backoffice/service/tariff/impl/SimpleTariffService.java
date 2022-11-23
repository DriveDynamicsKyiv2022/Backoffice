package com.griddynamics.backoffice.service.tariff.impl;

import com.griddynamics.backoffice.dao.tariff.ITariffDao;
import com.griddynamics.backoffice.model.Tariff;
import com.griddynamics.backoffice.service.tariff.ITariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleTariffService implements ITariffService {
    private final ITariffDao tariffDao;

    @Autowired
    public SimpleTariffService(ITariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Override
    public Tariff getTariff(String id) {
        return tariffDao.find(id);
    }

    @Override
    public List<Tariff> getTariffs(Pageable pageable) {
        return tariffDao.findAll(pageable);
    }

    @Override
    public Tariff addTariff(Tariff tariff) {
        return tariffDao.save(tariff);
    }

    @Override
    public Tariff updateTariff(String id, Tariff newTariff) {
        return tariffDao.updateEntity(id, newTariff);
    }

    @Override
    public boolean deleteTariff(String id) {
        return tariffDao.delete(id);
    }
}
