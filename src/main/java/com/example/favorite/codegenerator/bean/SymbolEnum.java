package com.example.favorite.codegenerator.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SymbolEnum {
    MODULENAME("@{moduleName}"),
    PACKAGENAME("@{packageName}"),
    CLASSNAME("@{className}"),
    TABLENAME("@{tableName}"),
    COLUMN("@{column}"),
    FIELD("@{field}"),
    DATATYPE("@{datatype}"),
    COMMENT("@{comment}");

    private String symbol;

    SymbolEnum(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static boolean isColumn(SymbolEnum Symbol) {
        return Symbol == COLUMN || Symbol == FIELD || Symbol == COMMENT;
    }

    public static boolean hasSymbol(String str, String symbol) {
        Pattern pattern = Pattern.compile(symbol.replace("{", "\\{"), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

}
