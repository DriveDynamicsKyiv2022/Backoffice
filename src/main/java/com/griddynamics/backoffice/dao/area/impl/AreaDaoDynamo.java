package com.griddynamics.backoffice.dao.area.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.dao.ReadWriteBaseDaoDynamo;
import com.griddynamics.backoffice.dao.area.IAreaDao;
import com.griddynamics.backoffice.exception.DatabaseException;
import com.griddynamics.backoffice.model.impl.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!local")
public class AreaDaoDynamo extends ReadWriteBaseDaoDynamo<Area> implements IAreaDao {
    @Autowired
    public AreaDaoDynamo(DynamoDBMapper dynamoDBMapper, DynamoDB dynamoDB, ObjectMapper objectMapper) {
        super(dynamoDBMapper, Area.class, "area", objectMapper, dynamoDB);
    }

    @Override
    public Area updateEntity(Area newEntity) {
        Area area = super.find(newEntity.getAreaId());
        area.setCoordinates(newEntity.getCoordinates());
        area.setCity(newEntity.getCity());
        area.setCountry(newEntity.getCountry());
        dynamoDBMapper.save(area);
        Area updatedArea = dynamoDBMapper.load(area);
        if (updatedArea == null) {
            throw new DatabaseException("couldn't save area");
        }
        return updatedArea;
    }
}
