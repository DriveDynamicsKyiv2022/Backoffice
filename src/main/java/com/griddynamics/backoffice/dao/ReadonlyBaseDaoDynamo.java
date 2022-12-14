package com.griddynamics.backoffice.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.model.IDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ReadonlyBaseDaoDynamo<T extends IDocument> implements IReadonlyBaseDao<T> {
    public static final DynamoDBScanExpression DEFAULT_DYNAMODB_SCAN_EXPRESSION = new DynamoDBScanExpression().withConsistentRead(false);
    protected final DynamoDBMapper dynamoDBMapper;
    protected final Class<T> entityClass;
    protected final String entityName;
    protected final String tableName;
    protected final ObjectMapper objectMapper;
    protected final DynamoDB dynamoDB;

    public ReadonlyBaseDaoDynamo(DynamoDBMapper dynamoDBMapper, Class<T> entityClass, String entityName,
                                 ObjectMapper objectMapper, DynamoDB dynamoDB) {
        System.out.println("----building dynamo");
        this.dynamoDBMapper = dynamoDBMapper;
        this.entityClass = entityClass;
        this.entityName = entityName;
        DynamoDBTable dbTable = entityClass.getAnnotation(DynamoDBTable.class);
        this.tableName = dbTable.tableName();
        this.objectMapper = objectMapper;
        this.dynamoDB = dynamoDB;
    }

    @Override
    public List<T> findAll() {
        return dynamoDBMapper.scan(entityClass, DEFAULT_DYNAMODB_SCAN_EXPRESSION);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withLimit(pageable.getPageSize() * (pageable.getPageNumber() + 1))
                .withConsistentRead(false);
        PaginatedScanList<T> result = dynamoDBMapper.scan(entityClass, scanExpression);
        List<T> entities = result.stream()
                .filter(entity -> isWithinPage(result.indexOf(entity), pageable))
                .toList();
        return new PageImpl<>(entities, pageable, dynamoDBMapper.count(entityClass, DEFAULT_DYNAMODB_SCAN_EXPRESSION));
    }

    @Override
    public T find(String id) {
        T entity = dynamoDBMapper.load(entityClass, id);
        if (entity == null) {
            throw new IllegalArgumentException("no such " + entityName);
        }
        return entity;
    }

    public Page<T> findByIndex(String indexName, Pageable pageable, Function<Index, ItemCollection<?>> itemCollectionFunction) {
        ItemCollection<?> itemCollection = itemCollectionFunction.apply(getIndex(indexName));
        List<Item> items = extractFromItemCollection(itemCollection);
        List<T> entities = items.stream()
                .filter(item -> isWithinPage(items.indexOf(item), pageable))
                .map(this::extractFromItem).toList();
        return new PageImpl<>(entities, pageable, itemCollection.getAccumulatedItemCount());
    }

    private boolean isWithinPage(int index, Pageable pageable) {
        return index < pageable.getPageSize() * (pageable.getPageNumber() + 1) && index >= pageable.getPageNumber() * pageable.getPageSize();
    }

    private Index getIndex(String indexName) {
        return dynamoDB.getTable(tableName).getIndex(indexName);
    }

    private List<Item> extractFromItemCollection(ItemCollection<?> queryOutcome) {
        List<Item> items = new ArrayList<>();
        for (com.amazonaws.services.dynamodbv2.document.Page<Item, ?> page : queryOutcome.pages()) {
            items.addAll(extractFromPage(page));
        }
        return items;
    }

    private List<Item> extractFromPage(com.amazonaws.services.dynamodbv2.document.Page<Item, ?> page) {
        List<Item> items = new ArrayList<>();
        for (Item item : page) {
            items.add(item);
        }
        return items;
    }

    private T extractFromItem(Item item) {
        try {
            return objectMapper.readValue(item.toJSON(), entityClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
