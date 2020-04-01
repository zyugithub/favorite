package com.example.favorite.codegenerator.bean;

import java.util.*;

public class Clazz {
    private String tableName;
    private String className;
    private Set<String> importTypes;
    private List<String> annotations;
    private List<Field> fields;

    public Clazz() {
        importTypes = new TreeSet<>();
        annotations = new ArrayList<>();
        fields = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Set<String> getImportTypes() {
        return importTypes;
    }

    public void addImportType(String importType) {
        importTypes.add(importType);
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(String annotation) {
        annotations.add(annotation);
    }

    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }

}
