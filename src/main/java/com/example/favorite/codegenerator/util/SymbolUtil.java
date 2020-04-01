package com.example.favorite.codegenerator.util;

import com.example.favorite.codegenerator.bean.Column;
import com.example.favorite.codegenerator.bean.SymbolEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.favorite.codegenerator.bean.SymbolEnum.hasSymbol;
import static com.example.favorite.codegenerator.util.DataTypeUtil.getSimpleName;
import static com.example.favorite.codegenerator.util.StringUtil.*;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

public class SymbolUtil {
    private static final String SYMBOL_PATTERN = "@\\{(\\w+)}";

    private static Map<SymbolEnum, Function<String, String>> tableFunc = new HashMap<>();
    private static Map<SymbolEnum, Function<Column, String>> columnFunc = new HashMap<>();

    static {
        tableFunc.put(SymbolEnum.MODULENAME, StringUtil::getPrefix);
        tableFunc.put(SymbolEnum.CLASSNAME, v -> getCamelCase(ignorePrefix(v), true));
        tableFunc.put(SymbolEnum.TABLENAME, v -> v);
        columnFunc.put(SymbolEnum.COLUMN, Column::getColumnName);
        columnFunc.put(SymbolEnum.FIELD, column -> getCamelCase(column.getColumnName(), false));
        columnFunc.put(SymbolEnum.COMMENT, Column::getColumnComment);
    }

    public static List<String> replace(String str, String tableName, List<Column> columns, Map<String, String> dataTypeMapping) {
        List<String> lines = new ArrayList<>();

        if (hasSymbol(str, SymbolEnum.FIELD.getSymbol())) {
            for (Column column : columns) {
                String temp = parse(str, tableName, column, dataTypeMapping);
                lines.add(temp);
            }
        } else {
            lines.add(replace(str, tableName));
        }

        return lines;
    }

    public static String replace(String str, String tableName) {
        return parse(str, tableName, null, null);
    }

    private static String parse(String str, String tableName, Column column, Map<String, String> dataTypeMapping) {
        Pattern pattern = Pattern.compile(SYMBOL_PATTERN);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String symbolName = matcher.group(1);
            SymbolEnum symbol = SymbolEnum.valueOf(symbolName.toUpperCase());
            String value = handleValue(tableName, column, symbol, dataTypeMapping);
            str = matcher.replaceFirst(caseSensitive(symbolName, value));
            matcher = pattern.matcher(str);
        }

        return  str;
    }

    private  static String caseSensitive(String symbolName, String value) {
        if (symbolName.equals(symbolName.toUpperCase())) {
            return value.toUpperCase();
        } else if (isLowerCase(symbolName.charAt(0))) {
            return capitalLower(value);
        } else if (isUpperCase(symbolName.charAt(0))) {
            return capitalUpper(value);
        }

        return value;
    }

    private static String handleValue(String tableName, Column column, SymbolEnum symbol, Map<String, String> dataTypeMapping) {
        if (symbol == SymbolEnum.DATATYPE) {  // 数据类型的映射涉及到外部变量dataTypeMapping，不适合function
            return getSimpleName(dataTypeMapping.get(column.getDataType()));
        } else if (SymbolEnum.isColumn(symbol)) {
            return columnFunc.get(symbol).apply(column);
        }

        return tableFunc.get(symbol).apply(tableName);
    }

}
