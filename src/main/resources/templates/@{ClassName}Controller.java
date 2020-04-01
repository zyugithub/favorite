package com.rising.mis.controller.@{moduleName};

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rising.mis.bo.@{moduleName}.@{ClassName}BO;
import com.rising.mis.common.CacheUser;
import com.rising.mis.common.CommonResponse;
import com.rising.mis.common.PaginatorVO;
import com.rising.mis.dto.@{moduleName}.@{ClassName}DTO;
import com.rising.mis.entity.@{moduleName}.@{ClassName};
import com.rising.mis.service.@{moduleName}.@{ClassName}Service;
import com.rising.mis.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/@{moduleName}")
public class @{ClassName}Controller {
    private static final String @{CLASSNAME}S = "/@{className}s";

    @Autowired
    private @{ClassName}Service @{className}Service;
    @Autowired
    private @{ClassName}BO @{className}BO;

    @GetMapping(@{CLASSNAME}S)
    public CommonResponse <List<@{ClassName}DTO>> get@{ClassName}s(@{ClassName} @{className}, Integer pi, Integer ps){
        CommonResponse<List<@{ClassName}DTO>> response = new CommonResponse<>();
        response.setSuccess(Boolean.TRUE);
        Page page = PageHelper.startPage(pi, ps);
        List<@{ClassName}DTO> @{className}s = @{className}Service.get@{ClassName}s(@{className});
        response.setResult(@{className}s);
        PaginatorVO paginator = new PaginatorVO();
        paginator.setTotalCount(page.getTotal());
        response.setPaginator(paginator);
        return response;
    }

    @PostMapping(@{CLASSNAME}S)
    public CommonResponse<@{ClassName}> add@{ClassName}(@RequestBody @{ClassName}DTO in) {
        CommonResponse<@{ClassName}> response = new CommonResponse<>();
        response.setSuccess(Boolean.TRUE);
        in.setCreateUserId(CacheUser.getCurrentUserId());
        in.setCreateTime(new Date());
        BeanUtils.defaultValue(in);
        @{className}BO.add@{ClassName}(in);
        response.setResult(in);
        return response;
    }

    @PutMapping(@{CLASSNAME}S)
    public CommonResponse<@{ClassName}> update@{ClassName}(@RequestBody @{ClassName}DTO in) {
        CommonResponse<@{ClassName}> response = new CommonResponse<>();
        response.setSuccess(Boolean.TRUE);
        BeanUtils.defaultValue(in);
        @{className}BO.update@{ClassName}(in);
        response.setResult(in);
        return response;
    }

    @DeleteMapping(@{CLASSNAME}S)
    public CommonResponse<Boolean> delete@{ClassName}s(@RequestBody List<@{ClassName}> ins) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        response.setSuccess(Boolean.TRUE);
        @{className}BO.delete@{ClassName}s(ins);
        response.setResult(Boolean.TRUE);
        return response;
    }

}
