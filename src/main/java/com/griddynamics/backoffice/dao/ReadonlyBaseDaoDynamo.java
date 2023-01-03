package com.griddynamics.backoffice.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.backoffice.exception.PaginationException;
import com.griddynamics.backoffice.exception.ResourceNotFoundException;
import com.griddynamics.backoffice.model.IDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        int totalCount = dynamoDBMapper.count(entityClass, DEFAULT_DYNAMODB_SCAN_EXPRESSION);
        if (!isValidPage(totalCount, pageable)) {
            throw new PaginationException("No such page");
        }
        PaginatedScanList<T> result = dynamoDBMapper.scan(entityClass, scanExpression);
        List<T> listedResult = result.stream().collect(Collectors.toList());
        List<T> entities = listedResult.stream()
                .filter(entity -> isWithinPage(listedResult.indexOf(entity), pageable))
                .collect(Collectors.toList());
        return new PageImpl<>(entities, pageable, totalCount);
    }

    @Override
    public T find(String id) {
        T entity = dynamoDBMapper.load(entityClass, id);
        if (entity == null) {
            throw new ResourceNotFoundException("No such " + entityName);
        }
        return entity;
    }

    public Page<T> findByIndex(String indexName, Pageable pageable, Function<Index, ItemCollection<?>> itemCollectionFunction) {
        ItemCollection<?> itemCollection = itemCollectionFunction.apply(getIndex(indexName));
        List<Item> items = extractFromItemCollection(itemCollection);
        int accumulatedItemCount = itemCollection.getAccumulatedItemCount();
        if (!isValidPage(accumulatedItemCount, pageable)) {
            throw new PaginationException("No such page");
        }
        List<T> entities = items.stream()
                .filter(item -> isWithinPage(items.indexOf(item), pageable))
                .map(this::extractFromItem).collect(Collectors.toList());
        return new PageImpl<>(entities, pageable, accumulatedItemCount);
    }

    private boolean isValidPage(int totalCount, Pageable pageable) {
        return pageable.getPageNumber() * pageable.getPageSize() < totalCount;
    }

    private boolean isWithinPage(int index, Pageable pageable) {
        return index < pageable.getPageSize() * (pageable.getPageNumber() + 1) && index >= pageable.getPageNumber() * pageable.getPageSize();
    }

    private Index getIndex(String indexName) {
        return getTable().getIndex(indexName);
    }

    private Table getTable() {
        return dynamoDB.getTable(tableName);
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
