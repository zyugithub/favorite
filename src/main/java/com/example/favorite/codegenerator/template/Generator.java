package com.example.favorite.codegenerator.template;

import com.example.favorite.codegenerator.bean.Column;

import java.util.List;

public interface Generator {
    String generate(String tableName, List<Column> columns);
}
