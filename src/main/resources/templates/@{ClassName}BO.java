package com.rising.mis.bo.@{moduleName};

import com.rising.mis.dto.@{moduleName}.@{ClassName}DTO;
import com.rising.mis.entity.@{moduleName}.@{ClassName};
import com.rising.mis.entity.@{moduleName}.@{ClassName}Detail;
import com.rising.mis.service.@{moduleName}.@{ClassName}DetailService;
import com.rising.mis.service.@{moduleName}.@{ClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class @{ClassName}BO {
    @Autowired
    private @{ClassName}Service @{className}Service;
    @Autowired
    private @{ClassName}DetailService detailService;

    public void add@{ClassName}(@{ClassName}DTO @{className}) {
        @{className}Service.save(@{className});
        add@{ClassName}Details(@{className});
    }

    public void update@{ClassName}(@{ClassName}DTO @{className}) {
        @{className}Service.update(@{className});
        del@{ClassName}Details(@{className});
        add@{ClassName}Details(@{className});
    }

    public void delete@{ClassName}s(List<@{ClassName}> @{className}s) {
        for (@{ClassName} @{className} : @{className}s) {
            @{className}Service.deleteByPK(@{className}.get@{ClassName}Id());
            del@{ClassName}Details(@{className});
        }
    }

    private void add@{ClassName}Details(@{ClassName}DTO @{className}) {
        List<@{ClassName}Detail> details = new ArrayList<>();

        for (@{ClassName}Detail detail : @{className}.getDetails()) {
            detail.set@{ClassName}Id(@{className}.get@{ClassName}Id());
            details.add(detail);
        }

        detailService.saveAll(details);
    }

    private void del@{ClassName}Details(@{ClassName} @{className}) {
        Condition condition = new Condition(@{ClassName}Detail.class);
        condition.createCriteria().andEqualTo("@{className}Id", @{className}.get@{ClassName}Id());
        List<@{ClassName}Detail> details = detailService.selectByCondition(condition);

        detailService.deleteByCondition(condition);
    }

}