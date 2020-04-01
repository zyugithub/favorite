package com.example.favorite.codegenerator.source;

import com.example.favorite.codegenerator.bean.Column;
import com.example.favorite.codegenerator.bean.Table;
import com.example.favorite.codegenerator.exception.ApplicationException;
import com.example.favorite.codegenerator.mapper.SchemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("table")
public class TableSource implements SourceStrategy {
    private SchemaMapper schemaMapper;

    @Autowired
    public TableSource(SchemaMapper schemaMapper) {
        this.schemaMapper = schemaMapper;
    }

    @Override
    public Table getTable(String tableName) {
        List<Column> columns = schemaMapper.getColumns(tableName);

        if (columns.size() == 0) {
            throw new ApplicationException("表 {0} 不存在", tableName);
        }

        return new Table(tableName, columns);
    }
}
