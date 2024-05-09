package com.mycompany.myapp.web.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;

public class CategoryRequestDto {

    @Getter
    public static class CreateCategoryDto{
        @ApiModelProperty(example = "친구")
        @ApiParam(name = "category", value = "카테고리 이름 입력", required = true)
        private String category;
    }
}
