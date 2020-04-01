package com.example.favorite.codegenerator.util;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.sql.JDBCType;
import java.util.Date;

public class DataTypeUtil {
    private static CaseInsensitiveMap jdbc2Java = new CaseInsensitiveMap();

    static {
//        jdbc
        jdbc2Java.put(JDBCType.VARCHAR.getName(), String.class.getName());
        jdbc2Java.put(JDBCType.BIGINT.getName(), Long.class.getName());
        jdbc2Java.put(JDBCType.INTEGER.getName(), Integer.class.getName());
        jdbc2Java.put(JDBCType.DOUBLE.getName(), Double.class.getName());
        jdbc2Java.put(JDBCType.DATE.getName(), Date.class.getName());
        jdbc2Java.put(JDBCType.TINYINT.getName(), Boolean.class.getName());
//        mysql
        jdbc2Java.put("datetime".toUpperCase(), Date.class.getName());
        jdbc2Java.put("int".toUpperCase(), Integer.class.getName());
    }

    public static CaseInsensitiveMap getJdbc2Java() {
        return jdbc2Java;
    }

    public static String getJavaDataType(String jdbcTypeName) {
        return (String) jdbc2Java.get(jdbcTypeName);
    }

    public static String getSimpleName(String className) {
        return className.substring(className.lastIndexOf('.') + 1);
    }

    public static boolean isPrimitive(String className) {
        return className.startsWith("java.lang");
    }
}
