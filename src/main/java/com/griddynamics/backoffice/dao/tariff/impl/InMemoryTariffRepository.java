package com.griddynamics.backoffice.dao.tariff.impl;

import com.griddynamics.backoffice.dao.tariff.ITariffRepository;
import com.griddynamics.backoffice.model.Tariff;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryTariffRepository implements ITariffRepository {
    private final List<Tariff> tariffs;

    public InMemoryTariffRepository() {
        this.tariffs = new ArrayList<>();
    }

    @Override
    public List<Tariff> getTariffsPaginated(Pageable pageable) {
        List<Tariff> tariffPage = new ArrayList<>();
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        if (pageSize * (pageNumber - 1) > tariffs.size()) {
            throw new IllegalArgumentException("No such page");
        }
        for (int i = pageSize * (pageNumber - 1); i < pageNumber * pageSize; i++) {
            tariffPage.add(tariffs.get(i));
        }
        return tariffPage;
    }

    @Override
    public Tariff addTariff(Tariff tariff) {
        tariffs.add(tariff);
        return tariffs.get(tariffs.size() - 1);
    }

    @Override
    public boolean deleteTariff(long id) {
        try {
            tariffs.remove((int) id);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public Tariff getTariff(long id) {
        try {
            return tariffs.get((int) id);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No such tariff");
        }
    }

    @Override
    public Tariff updateTariff(long id, Tariff newTariff) {
        try {
            tariffs.add((int) id, newTariff);
            return tariffs.get((int) id);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No such tariff");
        }
    }
}
