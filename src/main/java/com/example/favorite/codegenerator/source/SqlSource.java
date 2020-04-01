package com.example.favorite.codegenerator.source;

import com.example.favorite.codegenerator.bean.Column;
import com.example.favorite.codegenerator.bean.Table;
import com.example.favorite.codegenerator.exception.ApplicationException;
import com.example.favorite.codegenerator.mapper.SchemaMapper;
import com.example.favorite.codegenerator.sql.SqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component("sql")
public class SqlSource implements SourceStrategy {
    private static final String UNKNOWN_TABLE = "UNKNOWN";
    private SchemaMapper schemaMapper;

    @Autowired
    public SqlSource(SchemaMapper schemaMapper) {
        this.schemaMapper = schemaMapper;
    }

    @Override
    public Table getTable(String sql) {
        try {
            schemaMapper.explain(sql);
        } catch (Exception e) {
            throw new ApplicationException("sql语句存在错误");
        }

        SqlParser parser = new SqlParser(sql);
        Map<String, List<String>> sqlColumns = parser.getColumns();

        int columnCount = 0;
        String tableName = null;
        List<Column> columns = new ArrayList<>();

        List<String> unkonwnColumns = sqlColumns.get(UNKNOWN_TABLE);
        sqlColumns.remove(UNKNOWN_TABLE);

        for (Map.Entry<String, List<String>> entry : sqlColumns.entrySet()) {
            List<Column> cols = schemaMapper.getColumns(entry.getKey());
            List<String> sqlColumnNames = entry.getValue();

            if (unkonwnColumns != null) {
                sqlColumnNames.addAll(unkonwnColumns);
            }

            // select列最多的表作为主表
            if (columnCount < sqlColumnNames.size()) {
                columnCount = sqlColumnNames.size();
                tableName = entry.getKey();
            }

            for (String sqlColumnName : sqlColumnNames) {
                // select *
                if (sqlColumnName.equals("*")) {
                    columns.addAll(cols);
                    columnCount = cols.size();
                    tableName = entry.getKey();
                    continue;
                }

                Iterator<Column> it = cols.iterator();

                while (it.hasNext()) {
                    Column col = it.next();

                    if (col.getColumnName().equalsIgnoreCase(sqlColumnName)) {
                        columns.add(col);
                        it.remove();
                        break;
                    }
                }
            }
        }

        return new Table(tableName, columns);
    }
}
