package com.rising.mis.mapper.@{ModuleName};

import com.rising.mis.config.mybatis.BusinessMapper;
import com.rising.mis.dto.@{moduleName}.@{ClassName}DTO;
import com.rising.mis.entity.@{moduleName}.@{ClassName};
import com.rising.mis.sqlprovider.@{ModuleName}@{ClassName}Provider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface @{ClassName}Mapper extends BusinessMapper<@{ClassName}> {
    @SelectProvider(method = "search@{ClassName}s",type = @{ModuleName}@{ClassName}Provider.class)

    @Results({
            @Result(property = "@{field}",column="@{column}"),
    })
    public List<@{ClassName}DTO> get@{ClassName}s(@Param("@{className}") @{ClassName} @{className});
}
