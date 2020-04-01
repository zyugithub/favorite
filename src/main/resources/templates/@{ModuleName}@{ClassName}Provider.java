package com.rising.mis.sqlprovider;

import com.rising.mis.entity.@{moduleName}.@{ClassName};
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class @{ModuleName}@{ClassName}Provider {
    public String search@{ClassName}s(Map<String,Object> params){
        @{ClassName} @{className} = (@{ClassName}) params.get("@{className}");

        SQL sql=new SQL();
        sql.SELECT("*");
        sql.FROM("@{tableName} o");

        return sql.toString();
    }
}
