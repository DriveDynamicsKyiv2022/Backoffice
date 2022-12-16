package com.griddynamics.backoffice.dao.tariff.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.dao.ReadWriteBaseDaoDynamo;
import com.griddynamics.backoffice.dao.tariff.ITariffDao;
import com.griddynamics.backoffice.exception.DatabaseException;
import com.griddynamics.backoffice.model.impl.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!local")
public class TariffDaoDynamo extends ReadWriteBaseDaoDynamo<Tariff> implements ITariffDao {
    @Autowired
    public TariffDaoDynamo(DynamoDBMapper dynamoDBMapper, ObjectMapper objectMapper, DynamoDB dynamoDB) {
        super(dynamoDBMapper, Tariff.class, "tariff", objectMapper, dynamoDB);
    }

    @Override
    public Tariff updateEntity(Tariff newEntity) {
        Tariff tariff = super.find(newEntity.getTariffId());
        tariff.setName(newEntity.getName());
        tariff.setDescription(newEntity.getDescription());
        tariff.setCarBodyStyle(newEntity.getCarBodyStyle());
        tariff.setRatePerHour(newEntity.getRatePerHour());
        dynamoDBMapper.save(tariff);
        Tariff updatedTariff = dynamoDBMapper.load(tariff);
        if (updatedTariff == null) {
            throw new DatabaseException("Couldn't update tariff");
        }
        return updatedTariff;
    }
}
