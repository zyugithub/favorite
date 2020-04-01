package com.example.favorite.codegenerator.bean;

import java.util.List;

public class Table {
   private String tableName;
   private List<Column> columns;

   public Table(String tableName, List<Column> columns) {
       this.tableName = tableName;
       this.columns = columns;
   }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

}
