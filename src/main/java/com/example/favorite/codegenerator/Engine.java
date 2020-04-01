package com.example.favorite.codegenerator;

import com.example.favorite.codegenerator.bean.Column;
import com.example.favorite.codegenerator.bean.Table;
import com.example.favorite.codegenerator.source.SourceContext;
import com.example.favorite.codegenerator.template.Generator;
import com.example.favorite.codegenerator.template.GeneratorEnumFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Engine {
    @Value("${ignoredColumns}")
    private List<String> ignoredColumns;

    private Context context;
    private SourceContext sourceContext;

    @Autowired
    public Engine(Context context, SourceContext sourceContext) {
        this.context = context;
        this.sourceContext = sourceContext;
    }

    public List<String> generate(List<String> tableNames, GeneratorEnumFactory generatorEnumFactory) {
        return tableNames.stream()
                .map(tableName -> generate(tableName, generatorEnumFactory))
                .collect(Collectors.toList());
    }

    public String generate(String source, GeneratorEnumFactory generatorEnumFactory) {
        Table table = getTable(source);
        Generator gen = generatorEnumFactory.create(context);
        return gen.generate(table.getTableName(), filterColumns(table.getColumns()));
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private Table getTable(String source) {
        if (isSql(source)) {
            return sourceContext.getTable("sql", source);
        }

        return sourceContext.getTable("table", source);
    }

    private boolean isSql(String source) {
        return source.toLowerCase().startsWith("select ");
    }

    private List<Column> filterColumns(List<Column> columns) {
        if (context.isIgnoredColumns()) {
           return columns.stream()
                   .filter(column -> !ignoredColumns.contains(column.getColumnName()))
                   .collect(Collectors.toList());
        }

        return columns;
    }

}
