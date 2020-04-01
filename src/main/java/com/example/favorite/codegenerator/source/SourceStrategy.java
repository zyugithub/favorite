package com.example.favorite.codegenerator.source;

import com.example.favorite.codegenerator.bean.Table;

public interface SourceStrategy {
    Table getTable(String source);
}
