package com.example.favorite.codegenerator.mapper;

import com.example.favorite.codegenerator.bean.Column;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SchemaMapper {
    @Select({"SELECT c.column_name, c.data_type, c.column_comment, !ISNULL(kc.column_name) primary_key",
            "FROM information_schema.COLUMNS c",
            "LEFT JOIN information_schema.KEY_COLUMN_USAGE kc ON c.table_name = kc.table_name AND c.column_name = kc.column_name",
            "WHERE c.table_name = #{tableName}",
            "ORDER BY c.ordinal_position"})
    @Results({
            @Result(property = "columnName",  column = "column_name"),
            @Result(property = "dataType", column = "data_type"),
            @Result(property = "columnComment", column = "column_comment"),
            @Result(property = "primaryKey", column = "primary_key"),
    })
    List<Column> getColumns(@Param("tableName") String tableName);

    @Select("explain ${sql}")
    List<Map<String, Object>> explain(@Param("sql") String sql);
}
