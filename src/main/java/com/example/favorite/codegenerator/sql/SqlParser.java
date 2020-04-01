package com.example.favorite.codegenerator.sql;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;

import java.util.*;

public class SqlParser {
    private String sql;

    public SqlParser(String sql) {
        this.sql = sql;
    }

    public Map<String, List<String>> getColumns() {
        SQLStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement statement = parser.parseStatement();
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        Collection<TableStat.Column> list = visitor.getColumns();
        Map<String, List<String>> columns = new HashMap<>();

        // 去掉重复的列
        Set<String> distinctColumns = new HashSet<>();

        for (TableStat.Column column : list) {
            String columnName = column.getName();

            if (!distinctColumns.contains(columnName)) {
                distinctColumns.add(columnName);

                if (columns.containsKey(column.getTable())) {
                    columns.get(column.getTable()).add(columnName);
                } else {
                    List<String> columnNames = new ArrayList<>();
                    columnNames.add(columnName);
                    columns.put(column.getTable(), columnNames);
                }
            }
        }

        return columns;
    }

}
