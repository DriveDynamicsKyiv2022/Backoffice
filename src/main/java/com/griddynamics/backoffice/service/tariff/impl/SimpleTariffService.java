package com.griddynamics.backoffice.service.tariff.impl;

import com.griddynamics.backoffice.dao.tariff.ITariffDao;
import com.griddynamics.backoffice.model.impl.Tariff;
import com.griddynamics.backoffice.service.tariff.ITariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Tariff> getTariffs(Pageable pageable) {
        return tariffDao.findAll(pageable);
    }

    @Override
    public Tariff addTariff(Tariff tariff) {
        return tariffDao.save(tariff);
    }

    @Override
    public Tariff updateTariff(Tariff newTariff) {
        return tariffDao.updateEntity(newTariff);
    }

    @Override
    public boolean deleteTariff(String id) {
        return tariffDao.delete(id);
    }
}
