package com.example.favorite.codegenerator.source;

import com.example.favorite.codegenerator.bean.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SourceContext {
    private Map<String, SourceStrategy> strategyMap;

    @Autowired
    public SourceContext(Map<String, SourceStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public Table getTable(String type, String source) {
        SourceStrategy strategy = this.strategyMap.get(type);
        return strategy.getTable(source);
    }

}
