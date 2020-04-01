package com.rising.mis.service.@{moduleName};

import com.rising.mis.config.mybatis.BaseService;
import com.rising.mis.dto.@{moduleName}.@{ClassName}DTO;
import com.rising.mis.entity.@{moduleName}.@{ClassName};
import com.rising.mis.mapper.@{moduleName}.@{ClassName}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class @{ClassName}Service extends BaseService<@{ClassName}, Long> {
    public List<@{ClassName}DTO> get@{ClassName}s(@{ClassName} @{className}) {
        return ((@{ClassName}Mapper) mapper).get@{ClassName}s(@{className});
    }
}
